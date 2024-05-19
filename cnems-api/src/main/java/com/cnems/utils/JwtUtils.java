package com.cnems.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecretKeyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.PublicKey;
import java.util.Date;

@Service
public class JwtUtils {
    private static final String SECRET = "===========================ougabougadouga============================================";
    private static final long EXPIRATION_TIME = 60L;

    private final SecretKey secretKey = new SecretKey() {
        @Override
        public String getAlgorithm() {
            return getSigningKey().getAlgorithm();
        }

        @Override
        public String getFormat() {
            return getSigningKey().getFormat();
        }

        @Override
        public byte[] getEncoded() {
            return getSigningKey().getEncoded();
        }
    };

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

    public Claims decodeToken(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}
