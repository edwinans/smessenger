package com.smessenger.api.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtil {
    // Use a base64-encoded secret key (should be moved to config in production)
    private static final String SECRET = "ZmFrZXNlY3JldGtleWZvcmRldmVsb3BtZW50c2VjdXJpdHk="; // base64 for 'fakesecretkeyfordevelopmentsecurity'
    private static final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    private static final long EXPIRATION_MS = 86400000; // 1 day

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
