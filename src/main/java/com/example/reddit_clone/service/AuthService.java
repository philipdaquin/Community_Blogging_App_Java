package com.example.reddit_clone.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.reddit_clone.controller.RegisterRequest;
import com.example.reddit_clone.models.User;
import com.example.reddit_clone.models.VerificationToken;
import com.example.reddit_clone.repository.UserRepository;
import com.example.reddit_clone.repository.VerificationRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    /*
     * Find me the matching object in the SecurityConfig 
        @Autowired
     */
    private final PasswordEncoder passwordEncoder_;
    private final UserRepository userRepository;
    private final VerificationRepo verificatioRepo;
    /** 
     * @Transactional 
     * Spring creates proxies for all the classes annotated with . 
     * either on the class or on any of the methods
     */
    @Transactional
    public void signUp(RegisterRequest req) { 
        User user = new User();

        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder_.encode(req.getPassword()));
        user.setCreatedAt(Instant.now());
        user.setEnabled(false);

        // Save user 
        userRepository.save(user);

        generatedVerificationToken(user);
    }

    private String generatedVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        // Create  a new Verification token 
        var verifiedToken = new VerificationToken();
        verifiedToken.setToken(token);
        verifiedToken.setUser(user);

        verificatioRepo.save(verifiedToken);        
        return token;
    }

}

