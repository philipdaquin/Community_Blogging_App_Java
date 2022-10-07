package com.example.reddit_clone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit_clone.models.Post;
import com.example.reddit_clone.models.UserObject;
import com.example.reddit_clone.models.Vote;

// THIS IS SENSITIVE ie IT ACTS AS A SQL QUERY
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, UserObject currentUser);
}
