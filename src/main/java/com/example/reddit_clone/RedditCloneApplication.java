package com.example.reddit_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedditCloneApplication {

	public static void main(String[] args) {
		System.out.println("Welcome to the reddit clone");
		SpringApplication.run(RedditCloneApplication.class, args);
	}

}
