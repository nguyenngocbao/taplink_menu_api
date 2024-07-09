package taplink.network.menu.api.commons.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import taplink.network.menu.api.exceptions.AccessDeniedException;
import taplink.network.menu.api.models.User;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
public class JwtUtils {

    //todo: move to application.yaml
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final int MINUTES = 60;

    public static String generateToken(String email) {
        var now = Instant.now();
        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(MINUTES, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String extractUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    public static Boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getEmail()) || username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private static Claims getTokenBody(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException | ExpiredJwtException e) { // Invalid signature or expired token
            log.error("Access denied: " + e.getMessage());
            throw new AccessDeniedException("Access denied: " + e.getMessage());
        }
    }

    private static boolean isTokenExpired(String token) {
        Claims claims = getTokenBody(token);
        return claims.getExpiration().before(new Date());
    }

}
