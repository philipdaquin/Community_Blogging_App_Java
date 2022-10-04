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

import com.example.reddit_clone.dto.PostRequest;
import com.example.reddit_clone.dto.PostResponse;
import com.example.reddit_clone.service.PostService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = {"/api/posts"})
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostRequest> createPost(@RequestBody PostRequest req) { 
        // Save user inside the database
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.save(req));
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPost(postId));
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<PostResponse>> getAllPost() { 
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getAllPosts());
    }

    @GetMapping("/subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubReddit(Long postId) { 
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostsBySubReddit(postId));
    }

    @GetMapping("/{usernmae}/")
    public ResponseEntity<List<PostResponse>> getPostByUser(String username) { 
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostsByUser(username));
    }

}
