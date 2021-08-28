package com.opensource.todo.security.jwt;

import com.opensource.todo.user.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtConfig jwtConfig;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtConfig.getJwtExpiration())).signWith(Keys.hmacShaKeyFor(jwtConfig.getJwtSecret().getBytes()))
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtConfig.getJwtSecret().getBytes()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtConfig.getJwtSecret().getBytes()).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException e) {
            log.error("Don't trust the token: {}", e.getMessage());
        }
        return false;
    }
}
