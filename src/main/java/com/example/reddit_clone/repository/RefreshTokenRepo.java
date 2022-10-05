package com.example.reddit_clone.repository;

import java.util.Optional;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce.Cluster.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit_clone.dto.RefreshTokenRequest;
import com.example.reddit_clone.models.RefreshToken;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(RefreshTokenRequest token);
    void deleteByToken(String token);
}
