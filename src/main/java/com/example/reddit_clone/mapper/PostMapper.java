package com.example.reddit_clone.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.reddit_clone.dto.PostRequest;
import com.example.reddit_clone.dto.PostResponse;
import com.example.reddit_clone.models.Post;
import com.example.reddit_clone.models.SubReddit;
import com.example.reddit_clone.models.User;

@Mapper(componentModel = "spring")
public interface PostMapper {
    /**
     * Create Post from PostRequest, SubReddit and User
     * @param postRequest
     * @param subRedditName
     * @param user
     * @return
     */
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subredditName", source = "subRedditName")
    @Mapping(target = "voteCount", constant = "0")
    Post map(PostRequest postRequest, SubReddit subRedditName, User user);


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
    @Mapping(target = "subredditName", source = "subredditName.name")
    @Mapping(target = "userName", source="user.username")
    @Mapping(target = "voteCount", source="voteCount")
    PostResponse mapToDto(Post post);

   
}
