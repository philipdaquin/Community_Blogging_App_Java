package com.example.reddit_clone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reddit_clone.models.SubReddit;


public interface SubRedditRepo extends JpaRepository<SubReddit, Long> {

    Optional<SubReddit> findByName(String subredditName);
       
}
