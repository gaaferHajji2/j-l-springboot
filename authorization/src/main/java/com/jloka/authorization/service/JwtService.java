package com.jloka.authorization.service;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.spec.SecretKeySpec;

@Service
public class JwtService {
    
    private static final String SECRET_KEY = "mySecretKeyWhichIsVeryLongAndSecure1234567890";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 10 hours
    
    private SecretKeySpec getSigningKey() {
        byte[] bytes = Base64.getDecoder().decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(bytes, "HmacSHA256");
    }
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }
    
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }
    
    private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .subject(subject)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(getSigningKey())
        .compact();
    }
    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}