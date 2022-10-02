package com.example.reddit_clone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reddit_clone.dto.SubRedditDto;
import com.example.reddit_clone.service.SubRedditService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = {"/api/subreddit"})
@AllArgsConstructor
public class SubRedditController {
    
    private final SubRedditService subRedditService;

    /**
     * 
     * 
     * @param newSubReddit
     * @return
     */
    @PostMapping
    public ResponseEntity<SubRedditDto> createSubReddit(@RequestBody final SubRedditDto newSubReddit) { 
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(subRedditService.saveSubReddit(newSubReddit));
    }
    
    /**
     * 
     * 
     * @return
     */
    @GetMapping(value = {"/{id}"})
    public ResponseEntity<List<SubRedditDto>> getAllSubReddit(@PathVariable Long id) { 
        return ResponseEntity.status(HttpStatus.OK).body(subRedditService.getAll());
    }
}
