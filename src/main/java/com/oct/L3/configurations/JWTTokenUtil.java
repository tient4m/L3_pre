package com.oct.L3.configurations;

import com.oct.L3.entity.UserEntity;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JWTTokenUtil {
    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.secretKey}")
    private String secretKey;


    public String generateToken(UserEntity userEntity) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userEntity.getRole());
        claims.put("id", userEntity.getId());
        try
        {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userEntity.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }
        catch (Exception e)
        {
            throw new RuntimeException("Cannot create jwt token, error: "+e.getMessage());

        }
    }

    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String getSubject(String token) {
        return  extractClaim(token, Claims::getSubject);
    }

    public Integer extractId(String token) {
        return extractClaim(token, claims -> claims.get("id", Integer.class));
    }


}
