package com.example.reddit_clone.service;

import org.springframework.stereotype.Service;

import com.example.reddit_clone.dto.CommentRequest;
import com.example.reddit_clone.models.Post;
import com.example.reddit_clone.repository.CommentRepo;
import com.example.reddit_clone.repository.PostRepository;
import com.example.reddit_clone.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService; 

    public void save(CommentRequest newComment) { 
        System.out.println("✅ CommentService.save()");
        Post post = postRepository
            .findById(newComment.getPostId())
            .orElseThrow(() -> new IllegalStateException("Unable to find post id for comment"));
            
    }
}
