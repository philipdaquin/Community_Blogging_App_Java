package com.example.reddit_clone.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reddit_clone.dto.SubRedditDto;
import com.example.reddit_clone.mapper.SubRedditMapper;
import com.example.reddit_clone.repository.SubRedditRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubRedditService {

    private final SubRedditRepo subRedditRepo;
    private final SubRedditMapper subRedditMapper;
    /**
     * 
     * @param newSubReddit
     */
    @Transactional
    public SubRedditDto saveSubReddit(SubRedditDto newSubReddit) { 
        var toSave = subRedditRepo.save(subRedditMapper.mapDtoToSubReddit(newSubReddit));

        // Get id from database
        newSubReddit.setId(toSave.getId());
        return newSubReddit;
    }
    /**
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public List<SubRedditDto> getAll() {
        return subRedditRepo.findAll()
            .stream()
            .map(subRedditMapper::mapSubRedditToDto)
            .collect(Collectors.toList());
    }
    /**
     * 
     * @param subreddit1
     * @return
     */
    // private SubRedditDto mapToDto(SubReddit subreddit) {
    //     return SubRedditDto.builder()
    //         .name(subreddit.getName())
    //         .id(subreddit.getId())
    //         .numberOfPosts(subreddit.getPosts().size())
    //         .build();
    // }


  
}
