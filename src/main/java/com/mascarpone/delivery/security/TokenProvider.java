package com.mascarpone.delivery.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static com.mascarpone.delivery.utils.Constants.AUTH_TOKEN_EXPIRATION;
import static com.mascarpone.delivery.utils.Constants.TOKEN_SECRET;

@Slf4j
@Service
public class TokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    public String createToken(Authentication authentication) {
        var userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return buildAuthToken(userPrincipal.getId(), new Date());
    }

    public String buildAuthToken(UUID userUUID, Date currentDate) {
        return Jwts.builder()
                .setSubject(userUUID.toString())
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + AUTH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();
    }

    public UUID getUserIdFromToken(String token) {
        var claims = Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return UUID.fromString(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }

        return false;
    }
}
