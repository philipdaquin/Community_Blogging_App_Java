package com.example.reddit_clone.service;

import java.time.Instant;
import java.util.UUID;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reddit_clone.models.RefreshToken;
import com.example.reddit_clone.repository.RefreshTokenRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
    
    private final RefreshTokenRepo refreshTokenRepo;

    /**
     * Generates New Refresh Tokens
     * @return Refresh
     */
    public RefreshToken generateRefreshToken() {
        System.out.println("✅ RefreshTokenService.generateRefreshToken()");
        // Create new RefreshToken
        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setCreatedDate(Instant.now());
        // Save to database
        return refreshTokenRepo.save(token);
    }

    public void validateRefreshToken(String token) {
        System.out.println("✅ RefreshTokenService.validateRefreshToken()"); 
        refreshTokenRepo.findByToken(token)
            .orElseThrow(() -> new IllegalStateException("Invalid to get the token"));
    }
    public void deleteRefreshToken(String token) { 
        System.out.println("✅ RefreshTokenService.deleteRefreshToken()");
        refreshTokenRepo.deleteByToken(token);
    }

}
