package com.example.reddit_clone.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfiguration {

    public void configure(HttpSecurity http) throws Exception { 
        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/api/auth/**")
            .permitAll()
            .anyRequest()
            .authenticated();
    }

    /*
     * A bean is an object that is instantiated, assmebled and otherwise managed 
     * by a Spring IOC container 
     * 
     * A bean is simple one of many objects in your application 
     */
    @Bean
    PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder();
    }

    
}
