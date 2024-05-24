package com.beblink.springsecurity.service;

import com.beblink.springsecurity.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * The type Jwt service.
 */
@Service
public class JwtService {

    @Value("${security.jwt.expiration-minutes}")
    private long expirationMinutes;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    /**
     * Generate token string.
     *
     * @param user        the user
     * @param extraClaims the extra claims
     * @return the string
     */
    public String generateToken(User user, Map<String, Object> extraClaims) {

        var issuedAt = new Date(System.currentTimeMillis());
        var expiration = new Date(issuedAt.getTime() + (expirationMinutes * 60 * 1000));
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(generateKey())
                .compact();
    }

    private Key generateKey() {
        var secretAsBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    /**
     * Extract username string.
     *
     * @param jwt the jwt
     * @return the string
     */
    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }


    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith((SecretKey) generateKey()).build()
                .parseSignedClaims(jwt).getPayload();
    }
}
