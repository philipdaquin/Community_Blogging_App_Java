package com.example.reddit_clone.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.reddit_clone.dto.CommentRequest;
import com.example.reddit_clone.models.Comment;
import com.example.reddit_clone.models.Post;
import com.example.reddit_clone.models.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    /**
     * 
     * @param commentRequest
     * @param post
     * @param user
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentRequest.text")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    Comment map(CommentRequest commentRequest, Post post, User user);


    /**
     * 
     * @param comment
     * @return
     */
    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentRequest commentToDto(Comment comment);
}
