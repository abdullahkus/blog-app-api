package com.ak.blogapp.security;

import com.ak.blogapp.exception.TokenValidationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String JWT_SECRET;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Map<String, Object> claims = new HashMap<>();
        claims.put("dene", "dene");

        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // get username from Jwt token
    private Date extractExpiration(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public String extractUser(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token)  {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new TokenValidationException("Malformed JWT: " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            throw new TokenValidationException("JWT expired: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            throw new TokenValidationException("Unsupported JWT: " + ex.getMessage());
        } catch (SignatureException ex) {
            throw new TokenValidationException("Invalid JWT signature: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new TokenValidationException("Invalid JWT argument: " + ex.getMessage());
        }
    }
}
