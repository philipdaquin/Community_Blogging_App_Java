package com.example.reddit_clone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit_clone.models.Comment;
import com.example.reddit_clone.models.Post;
import com.example.reddit_clone.models.UserObject;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
    List<Comment> findByUser(UserObject user);
}
