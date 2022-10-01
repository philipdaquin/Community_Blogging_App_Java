package com.example.reddit_clone.dto;

import com.example.reddit_clone.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubRedditDto {
    private Long id;
    private String SubRedditName;
    private String description;
    private Integer numberOfPosts;

}
