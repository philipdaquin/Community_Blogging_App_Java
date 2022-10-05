package com.example.reddit_clone.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String authentiticatedToken;
    private String username;

    private Instant expiresAt;
    private String refreshToken;
}
