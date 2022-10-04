package com.example.reddit_clone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit_clone.models.Post;
import com.example.reddit_clone.models.SubReddit;
import com.example.reddit_clone.models.User;


/*
 * JPA - Java Persistence 
 * - It is used to persist data between Java object and relational 
 *   database
 * - JPA acts as a bridge between object oriented domain models and relational
 *   database systems
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findBySubredditName(SubReddit subredditName);
    List<Post> findByUser(User user);

}
