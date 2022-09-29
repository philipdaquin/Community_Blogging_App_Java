package com.example.reddit_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit_clone.models.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    
}
