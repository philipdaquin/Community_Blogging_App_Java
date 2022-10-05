package com.example.reddit_clone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit_clone.models.Post;
import com.example.reddit_clone.models.User;
import com.example.reddit_clone.models.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserVoteIdDesc(Post post, User currentUser);
}
