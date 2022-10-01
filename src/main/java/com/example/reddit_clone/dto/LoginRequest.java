package com.example.reddit_clone.dto;

import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
