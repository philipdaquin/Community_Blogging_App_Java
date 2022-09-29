package com.example.reddit_clone.models;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubReddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "subreddit cannot be empty")
    private String name;

    @NotBlank(message = "Description cannot be left empty")
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts; 
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
