package com.example.reddit_clone.dto;

import com.example.reddit_clone.models.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubRedditDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;

}
