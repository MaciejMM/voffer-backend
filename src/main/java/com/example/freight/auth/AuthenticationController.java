package com.example.freight.auth;


import com.example.freight.auth.models.request.RefreshTokenRequest;
import com.example.freight.auth.models.entity.User;
import com.example.freight.auth.models.response.LoginResponse;
import com.example.freight.auth.models.request.LoginUserDto;
import com.example.freight.auth.models.request.RegisterUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        final User authenticatedUser = authenticationService.authenticate(loginUserDto);
        final String jwtToken = jwtService.generateToken(authenticatedUser);
        final String refreshToken = jwtService.generateRefreshToken(authenticatedUser); // Generate refresh token

        final LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .refreshToken(refreshToken)
                .build();

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshTokenRequest token) {
        String newAccessToken = jwtService.refreshToken(token.getToken());
        String newRefreshToken = jwtService.generateRefreshToken(jwtService.extractUserDetails(token.getToken()));
        LoginResponse loginResponse = LoginResponse.builder()
                .token(newAccessToken)
                .expiresIn(jwtService.getExpirationTime())
                .refreshToken(newRefreshToken)
                .build();
        return ResponseEntity.ok(loginResponse);
    }

}
