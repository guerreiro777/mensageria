package com.br.mensageria.producer.security.jwt;

import com.br.mensageria.producer.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Log
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration_in_ms}")
    private int jwtExpirationMs;

    private final int jwtExpirationMsForAdmin = 86400000;

    @Value("${environment.name}")
    private String environment;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        // Bypass admin em ambiente local. Gera o token com validade de um dia
        if (environment.equals("localhost") && userPrincipal.getUsername().equals("admin")) {
            this.jwtExpirationMs = jwtExpirationMsForAdmin;
        }

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.severe("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            log.severe("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            log.severe("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.severe("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.severe("JWT claims string is empty: " + e.getMessage());
        }

        return false;
    }
}
