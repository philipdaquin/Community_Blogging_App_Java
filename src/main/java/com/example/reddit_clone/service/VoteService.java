package com.example.reddit_clone.service;


import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reddit_clone.dto.VoteRequest;
import com.example.reddit_clone.mapper.VoteMapper;
import com.example.reddit_clone.models.Post;
import com.example.reddit_clone.models.Vote;
import com.example.reddit_clone.models.VoteType;
import com.example.reddit_clone.repository.PostRepository;
import com.example.reddit_clone.repository.VoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {
    private final PostRepository postRepository;
    private final AuthService authService;
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;

    @Transactional
    public void vote(VoteRequest req) {
        // Get post 
        Post post = postRepository
            .findById(req.getPostId())
            .orElseThrow(() -> new IllegalStateException("Post Not Found!"));    
        // 
        Optional<Vote> votesUnderPost = voteRepository
            .findTopByPostAndUserVoteIdDesc(post, authService.getCurrentUser());
        
        if (votesUnderPost.isPresent() && 
            votesUnderPost
                .get()
                .getVoteType()
                .equals(req.getVoteType())) {
            throw new IllegalStateException("You have already voted for this post!" + req.getPostId());
        }
        if (VoteType.UPVOTE.equals(req.getVoteType())) 
            post.setVoteCount(post.getVoteCount() + 1);
        else 
            post.setVoteCount(post.getVoteCount() - 1);
        
        voteRepository.save(mapToDto(req, post));
        postRepository.save(post);

    }

    /**
     * Build vote from post and user input
     * @param req
     * @param post
     * @return
     */
    private Vote mapToDto(VoteRequest req, Post post) {
        return Vote.builder()
            .voteType(req.getVoteType())
            .post(post)
            .user(authService.getCurrentUser())
            .build();
    }   
}
