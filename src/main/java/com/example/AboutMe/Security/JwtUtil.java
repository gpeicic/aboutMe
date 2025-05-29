package com.example.AboutMe.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

public final class JwtUtil {
    private static final String secretKey = "rca.pibsaorcibu234lbu43abcdefghijklmno";

    private static final SecretKey key =Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(Integer id, String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 sati
                .signWith(key)
                .compact();
    }

    public static LoggedInUser extractUser(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Integer id = claims.get("id", Integer.class);
            return new LoggedInUser(
                    id,
                    claims.getSubject(),
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        } catch (JwtException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
