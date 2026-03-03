package com.food.service.user;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service to manage JWT token blacklist for logout functionality
 * In production, consider using Redis or database for distributed systems
 */
@Service
public class TokenBlacklistService {

    // Thread-safe set to store blacklisted tokens
    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    /**
     * Add token to blacklist (during logout)
     * @param token JWT token to blacklist
     */
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    /**
     * Check if token is blacklisted
     * @param token JWT token to check
     * @return true if token is blacklisted, false otherwise
     */
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    /**
     * Remove token from blacklist (optional)
     * @param token JWT token to remove
     */
    public void removeTokenFromBlacklist(String token) {
        blacklistedTokens.remove(token);
    }

    /**
     * Clear all blacklisted tokens (optional)
     */
    public void clearBlacklist() {
        blacklistedTokens.clear();
    }
}

