package com.example.reddit_clone.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.reddit_clone.models.RefreshToken;
import com.example.reddit_clone.repository.RefreshTokenRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RefreshTokenService {
    
    private final RefreshTokenRepo refreshTokenRepo;

    /**
     * 
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

}
