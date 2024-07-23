package com.mdmuzzammilrashid.expensetracker.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServicesImplementation implements IJWTServices {

    @Autowired
    private UserServiceImplementation userService;

    @Value("${security.secret-key}")
    private String SECRET_KEY;

    @Value("${security.jwt.access-token-expiration}")
    private Long ACCESS_TOKEN_EXPIRE;

    @Value("${security.jwt.refresh-token-expiration}")
    private Long REFRESH_TOKEN_EXPIRE;

    @Override
    public String generateRefreshToken(String userId, String username) {
        Map<String, Object> claims = new HashMap<>(); // for send other information with userId
        claims.put("userId", userId);
        return createRefreshToken(claims, userId);

    }

    public String generateAccessToken(String userId, String username){
        Map<String, Object> claims = new HashMap<>(); // for send other information with userId
        claims.put("userId", userId);
        return createAccessToken(claims, username);
    }



    public String createAccessToken(Map<String, Object> claims, String subject) {
        return Jwts
        .builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE)) 
        .signWith(getSigningKeys())
        .compact();    
    }

    public String createRefreshToken (Map<String, Object> claims, String subject){
        return Jwts
        .builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE))
        .signWith(getSigningKeys())
        .compact();
    }

    private SecretKey getSigningKeys(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    @Override
    public Boolean validateToken(String token, String userId) {
        return extractClaim(token, Claims::getSubject).equalsIgnoreCase(userId);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSigningKeys())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date (System.currentTimeMillis()));
    }

    @Override
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String extractUserId(String token){
        return extractAllClaims(token).get("userId", String.class);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    
}
