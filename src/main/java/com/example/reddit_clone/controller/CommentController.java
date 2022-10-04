package com.example.reddit_clone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reddit_clone.dto.CommentRequest;
import com.example.reddit_clone.repository.CommentRepo;
import com.example.reddit_clone.repository.PostRepository;
import com.example.reddit_clone.repository.UserRepository;
import com.example.reddit_clone.service.CommentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {

    private final CommentRepo commentRepo;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;


    @PostMapping(value = "")
    public ResponseEntity<Void> createComment(@RequestBody CommentRequest req) { 
        commentService.save(req);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<CommentRequest>> getAllCommentsForPost(@PathVariable Long postId) { 
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getAllCommentsForPost(postId));
    }


}
