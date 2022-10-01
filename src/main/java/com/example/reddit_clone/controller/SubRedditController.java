package com.example.reddit_clone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reddit_clone.dto.SubRedditDto;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = {"/api/subreddit"})
@AllArgsConstructor
public class SubRedditController {
    
    @PostMapping
    public void createSubReddit(@RequestBody final SubRedditDto newSubReddit) { 
        return;
    }
    
}
