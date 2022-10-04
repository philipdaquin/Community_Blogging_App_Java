package com.example.reddit_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String title;
    private String url;
    private String description;
    private String userName;
    private String subredditName;

    private Integer voteCount;

    // private Integer commentCount;
    // private String duration;
    // private boolean upVote;
    // private boolean downVote;
}
