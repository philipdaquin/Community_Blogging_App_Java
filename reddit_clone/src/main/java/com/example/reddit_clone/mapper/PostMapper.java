package com.example.reddit_clone.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.reddit_clone.dto.PostRequest;
import com.example.reddit_clone.dto.PostResponse;
import com.example.reddit_clone.models.Post;
import com.example.reddit_clone.models.SubReddit;
import com.example.reddit_clone.models.User;
import com.example.reddit_clone.repository.CommentRepo;
import com.example.reddit_clone.repository.VoteRepository;
import com.example.reddit_clone.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private VoteRepository voteRepository;
    
    @Autowired
    private AuthService authService;

    /**
     * Create Post from PostRequest, SubReddit and User
     * @param postRequest
     * @param subRedditName
     * @param user
     * @return
     */
    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "url", source = "postRequest.url")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subredditName", source = "subRedditName")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post map(PostRequest postRequest, SubReddit subRedditName, User user);


    /**
     * 
     * Create a PostResponse for Post
     * @param post
     * @return
     */
    @Mapping(target = "postId", source = "postId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "url", source= "url")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "userName", source="user.username")
    @Mapping(target = "subredditName", source = "subredditName.name")
    @Mapping(target = "voteCount", source="voteCount")
    @Mapping(target = "commentCount", expression = "java(countCommentsByPost(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    /**
     * Count Duration how long it was posted ago 
     * @param post
     * @return
     */
    String getDuration(Post post) { 
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());   
    }

    /**
     * Count comments by posts
     * @param post
     * @return Comment Count
     */
    Integer countCommentsByPost(Post post) { 
        return commentRepo.findByPost(post).size();
    }
}
