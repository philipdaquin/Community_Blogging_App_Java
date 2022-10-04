package com.example.reddit_clone.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reddit_clone.dto.CommentRequest;
import com.example.reddit_clone.repository.CommentRepo;
import com.example.reddit_clone.repository.PostRepository;
import com.example.reddit_clone.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {

    private final CommentRepo commentRepo;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @PostMapping(value = "")
    public void createComment(@RequestBody CommentRequest req) { 
        
    }

}
