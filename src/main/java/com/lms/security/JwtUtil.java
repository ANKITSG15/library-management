package com.lms.security;

import com.lms.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtil {
    private final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final int EXPIRY_TIME_IN_MILLIS = 1000 * 60 * 60 * 10;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_TIME_IN_MILLIS))
                .signWith(KEY)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

}
