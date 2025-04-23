package org.veedev.authservice.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private final String secret = "VGhpcyBpcyBhIHZlcnkgc2VjdXJlIHNlY3JldCBrZXkgdGhhdCBoYXMgYWxsb3Qgb2YgYnl0ZXMgdGhhdCBhcmUgbmV2ZXIgcmV2ZWFsZWQh";
    private final long expiration = 3600000;

    public String generationToken(String phoneNumber, Long clientId) {
        return Jwts.builder()
                .claim("phoneNumber", phoneNumber)
                .claim("clientId", clientId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }
}
