package com.example.reddit_clone.models;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// Class is an entity 
@Entity
// Generate Getters
@Data
// Initiallise constructors
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    
    @NotBlank(message = "Title cannot be left blank")
    private String title;

    @Nullable
    private String url;

    // Nullable value 
    @Nullable
    // A large object declared for the database
    @Lob
    private String description;
    private Integer voteCount;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Subreddit subredditName;



}
