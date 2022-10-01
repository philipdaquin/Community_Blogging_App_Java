package com.example.reddit_clone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reddit_clone.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value ={"/api/auth"})
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    /**
     * Post Mapping for SignUp 
     * - RegisterReq contains User Object
     * - User Object is saved to the database 
     * - VerificationToken is generated and sent to the User
     * @param registerReq
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerReq) {
        authService.signUp(registerReq);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }

    /**
     * Get Mapping for the activation link inside the email template
     * - Receives the user token and a lookup function is activated
     * - Lookup function finds the user and set 'setEnabled' inside the database
     * @param token
     * @return
     */
    @GetMapping("/accountVerification/")
    public ResponseEntity<String> sendVerificationLink(@PathVariable String token) { 
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated", HttpStatus.OK);
            
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginReq) { 
        authService.loginUser(loginReq);
        return new ResponseEntity<>("Successful login!", HttpStatus.OK);
    }
}
