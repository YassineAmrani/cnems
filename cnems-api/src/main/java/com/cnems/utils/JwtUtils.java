package com.cnems.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtils {
    private static final String SECRET = "ougabougadouga";
    private static final long EXPIRATION_TIME = 60L;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(this.getSigningKey())
                .compact();
    }
}
