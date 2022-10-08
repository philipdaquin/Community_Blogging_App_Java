package com.example.reddit_clone.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import com.example.reddit_clone.dto.PostRequest;
import com.example.reddit_clone.dto.PostResponse;
import com.example.reddit_clone.mapper.PostMapper;
import com.example.reddit_clone.models.Post;
import com.example.reddit_clone.models.SubReddit;
import com.example.reddit_clone.models.UserObject;
import com.example.reddit_clone.repository.PostRepository;
import com.example.reddit_clone.repository.SubRedditRepo;
import com.example.reddit_clone.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "postCache")
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final SubRedditRepo subRedditRepo;
    private final AuthService authService;
    private final UserRepository userRepository;


    public PostRequest save(PostRequest postRequest) {
        
        // get subreddit from postrequest
        System.out.println("✅ Retrieving the subreddit from the subreddit name!");
        SubReddit subreddit = subRedditRepo
            .findByName(postRequest.getSubredditName())
            .orElseThrow(() -> new IllegalStateException("Unable to find SubReddit By Name"));
        
        System.out.println("✅ Getting the current context --> get user");
        UserObject user = authService.getCurrentUser();
        
        System.out.println("✅ Mapping post from the DTO");
        Post dtoConversion = postMapper.map(postRequest, subreddit, user);
        
        System.out.println("✅ Saving User");
        postRepository.save(dtoConversion);
        return postRequest;
    }
    /**
     * Get post by id 
     * 
     * @param postId
     * @return
     * @throws Exception
     */
    @Cacheable(cacheNames = "posts", key = "#postId")
    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) { 
        System.out.println("✅ PostService.getPost()");
        return postRepository.findById(postId)
            .map(postMapper::mapToDto)
            .orElseThrow(() -> new IllegalStateException("Post doesn't exist!"));
    }

    /**
     * 
     * Get all posts available in the database
     * @return
     */
    @Cacheable(cacheNames = "posts")
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() { 
        System.out.println("✅ PostService.getAllPosts()");
        return postRepository.findAll()
            .stream()
            .map(postMapper::mapToDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all posts by subreddit 
     * @param subRedditId
     * @return
     */
    @Cacheable(cacheNames = "posts", key = "#subRedditId")
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubReddit(Long subRedditId) { 
        System.out.println("✅ PostService.getPostsBySubReddit()");
        SubReddit subreddit = subRedditRepo
            .findById(subRedditId)
            .orElseThrow(() -> new IllegalStateException("Unable to find subreddit by id"));
        
        System.out.println("✅ Wrapping up result!");
        return postRepository.findBySubredditName(subreddit)
            .stream()
            // .filter(i -> i.getSubredditName().toString().equals(subRedditName))
            .map(postMapper::mapToDto)
            .collect(Collectors.toList());
    }

    /**
     * 
     * @param username
     * @return
     */
    @Cacheable(cacheNames = "posts", key = "#username")
    public List<PostResponse> getPostsByUser(String username) { 
        System.out.println("✅ PostService.getPostsByUser()");
        var user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new IllegalStateException("Unable to get the user in database!"));
        return postRepository.findByUser(user)
            .stream()
            .map(postMapper::mapToDto)
            .collect(Collectors.toList());
    }

    
}
