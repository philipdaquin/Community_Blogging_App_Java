package com.example.reddit_clone.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.reddit_clone.security.JwtAuthenticationFilter;
import com.example.reddit_clone.service.UserDetailsServiceImpl;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
 
    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 
     * 
     * @param http
     * @throws Exception
     */
    // public void configure(HttpSecurity http) throws Exception { 
    //     System.out.println("🍾 SecurityConfig.configure()");
    //     http.csrf()
    //         .disable()
    //         .authorizeRequests()
    //         .antMatchers("/api/auth/**")
    //         .permitAll()
    //         .antMatchers(HttpMethod.GET, "/api/subreddit")
    //         .permitAll()
    //         .antMatchers(
    //             "/v2/api-docs",
    //             "/configuration/ui",
    //             "/swagger-resources/**",
    //             "/configuration/security",
    //             "swagger-ui.html",
    //             "/webjars/**")
    //         .permitAll()
    //         .anyRequest()
    //         .authenticated();
    //     http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
        http.cors().and()
        .csrf().disable()
        .authorizeHttpRequests(authorize -> authorize
                .antMatchers("/api/auth/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/subreddit")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/**")
                .permitAll()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                );
        return http.build();
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

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authManager) { 
        // authManager.userDetailsService(userDetailsService)
        //     .passwordEncoder(passwordEncoder());
    }

    
}
