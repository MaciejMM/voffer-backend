package com.example.freight.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.expirationTime}")
    private long jwtExpiration;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.audience}")
    private String audience;

    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(final String token,final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(final UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateRefreshToken(final UserDetails userDetails) {
        long refreshTokenExpiration = jwtExpiration * 2; // Example: double the expiration time for refresh tokens
        return buildToken(new HashMap<>(), userDetails, refreshTokenExpiration);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            final UserDetails userDetails,
            long expiration
    ) {

        extraClaims.put("roles", userDetails.getAuthorities());
        extraClaims.put("email", userDetails.getUsername());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuer(issuer)
                .setAudience(audience)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String refreshToken(final String token) {
        final Claims claims = extractAllClaims(token);
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public UserDetails extractUserDetails(final String token) {
        String username = extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }

    public boolean isTokenValid(final String token, final UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
