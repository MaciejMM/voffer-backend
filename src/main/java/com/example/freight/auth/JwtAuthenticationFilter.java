package com.example.freight.auth;

import com.example.freight.auth.models.entity.User;
import com.example.freight.exception.CustomErrorHandler;
import com.example.freight.exception.UserNotFoundException;
import com.kinde.token.KindeToken;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String ROLE_PREFIX = "ROLE_";
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private final UserRepository userRepository;
    private final CustomErrorHandler customErrorHandler;
    private final TokenUtils tokenUtils;

    public JwtAuthenticationFilter(
            final UserRepository userRepository,
            final CustomErrorHandler customErrorHandler,
            final TokenUtils tokenUtils) {
        this.userRepository = userRepository;
        this.customErrorHandler = customErrorHandler;
        this.tokenUtils = tokenUtils;
    }


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER_KEY);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            KindeToken parse = tokenUtils.decodeToken(authHeader);
            final String userId = parse.getUser();
            final User user = userRepository.findByKindeId(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

            List<GrantedAuthority> authorities = ((List<?>) parse.getClaim("roles"))
                    .stream()
                    .filter(role -> role instanceof Map)
                    .map(role -> (Map<?, ?>) role)
                    .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.get("key")))
                    .collect(Collectors.toList());

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getKindeId(),
                    "",
                    authorities
            );

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    authorities
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            customErrorHandler.resolveException(request, response, null, exception);
        }
    }
}
