package com.olympiads.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication) {
        JwtUser user = (JwtUser) authentication.getPrincipal();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp expiryDate = new Timestamp(now.getTime() + SecurityConstants.EXPIRATION_TIME);

        String userId = user.getId().toString();

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", userId);
        claimsMap.put("email", user.getEmail());
        claimsMap.put("name", user.getName());
        claimsMap.put("surname", user.getSurname());

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .setExpiration(expiryDate)
                .setSubject(userId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException |
                MalformedJwtException |
                ExpiredJwtException |
                IllegalArgumentException |
                UnsupportedJwtException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public UUID getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();

        return UUID.fromString((String) claims.get("id"));
    }
}
