package com.smessenger.api.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import java.security.MessageDigest;

import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
    private static volatile SecretKey key;
    private static final long EXPIRATION_MS = 86400000; // 1 day

    @Value("${jwt.secret-location}")
    private Resource secretResource;

    @PostConstruct
    public void init() {
        try {
            String content = StreamUtils
                    .copyToString(
                            secretResource.getInputStream(),
                            java.nio.charset.StandardCharsets.UTF_8)
                    .trim();
            if (content.isBlank()) {
                throw new IllegalStateException("JWT secret file is empty: " + secretResource);
            }
            byte[] keyBytes = content.getBytes(StandardCharsets.UTF_8);
            if (keyBytes.length < 32) {
                // Force a 32-byte key
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    keyBytes = md.digest(keyBytes);
                } catch (Exception ex) {
                    throw new IllegalStateException("Failed to derive JWT key", ex);
                }
            }
            key = Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load JWT secret from " + secretResource, e);
        }
    }

    public static String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_MS);
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    public static String extractUsername(String token) {
        return getClaims(token).getPayload().getSubject();
    }

    public static String extractUsernameFromAuthorizationHeader(String authorization) {
        if (authorization == null)
            throw new IllegalArgumentException("Missing Authorization header");
        String token = authorization.startsWith("Bearer ") ? authorization.substring(7) : authorization;
        return extractUsername(token);
    }

    public static boolean validateToken(String token, String username) {
        String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    private static Jws<Claims> getClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    }

    private static boolean isTokenExpired(String token) {
        return getClaims(token).getPayload().getExpiration().before(new Date());
    }
}
