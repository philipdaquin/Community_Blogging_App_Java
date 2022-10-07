package com.example.reddit_clone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit_clone.models.UserObject;

@Repository
public interface UserRepository extends JpaRepository<UserObject, Long> {

    Optional<UserObject> findByUsername(String username);

    
}
