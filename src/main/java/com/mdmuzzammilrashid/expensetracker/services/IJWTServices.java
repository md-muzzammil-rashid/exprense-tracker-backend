package com.mdmuzzammilrashid.expensetracker.services;

import java.util.Date;

import io.jsonwebtoken.Claims;

public interface IJWTServices {
    public Boolean validateToken(String token, String userId);
    public Claims extractAllClaims(String token);
    public Boolean isTokenExpired(String token);
    public Date extractExpiration(String token);
    public String generateRefreshToken(String userId, String username);
    public String generateAccessToken(String userId, String username);
    public String extractUserId(String token);
    public String extractUsername(String token);
}
