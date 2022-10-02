package com.example.reddit_clone.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reddit_clone.dto.SubRedditDto;
import com.example.reddit_clone.models.SubReddit;
import com.example.reddit_clone.repository.SubRedditRepo;
import com.fasterxml.jackson.core.sym.Name;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubRedditService {

    private final SubRedditRepo subRedditRepo;
    
    /**
     * 
     * @param newSubReddit
     */
    @Transactional
    public SubRedditDto saveSubReddit(SubRedditDto newSubReddit) { 
        var subReddit = mapSubRedditDto(newSubReddit);
        subRedditRepo.save(subReddit);

        // Get id from database
        newSubReddit.setId(subReddit.getId());

        return newSubReddit;
    }
    /**
     * 
     * @param newSubReddit
     * @return SubReddit 
     */
    private SubReddit mapSubRedditDto(SubRedditDto newSubReddit) {
        return SubReddit.builder()
            .name(newSubReddit.getName())
            .description(newSubReddit.getDescription())
            .build();
    }
    /**
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public List<SubRedditDto> getAll() {
        return subRedditRepo.findAll()
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }
    /**
     * 
     * @param subreddit1
     * @return
     */
    private SubRedditDto mapToDto(SubReddit subreddit1) {
        return SubRedditDto.builder()
            .name(subreddit1.getName())
            .id(subreddit1.getId())
            .numberOfPosts(subreddit1.getPosts().size())
            .build();
    }
  
}
