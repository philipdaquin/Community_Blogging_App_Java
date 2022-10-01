package com.example.reddit_clone.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.reddit_clone.controller.LoginRequest;
import com.example.reddit_clone.controller.RegisterRequest;
import com.example.reddit_clone.models.NotificationEmail;
import com.example.reddit_clone.models.User;
import com.example.reddit_clone.models.VerificationToken;
import com.example.reddit_clone.repository.UserRepository;
import com.example.reddit_clone.repository.VerificationRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    /*
     * Find me the matching object in the SecurityConfig 
        @Autowired
     */
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationRepo verificatioRepo;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    /** 
     * @Transactional 
     * Spring creates proxies for all the classes annotated with . 
     * either on the class or on any of the methods
     */
    public void signUp(RegisterRequest req) { 
        System.out.println("✅ AuthService.signUp()");
        User user = new User();

        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setCreatedAt(Instant.now());
        user.setEnabled(false);

        // Save user 
        userRepository.save(user);
        var token = generateVerificationToken(user);
        // 
        mailService.sendMail(new NotificationEmail(
            "Spring Boot -- Activate your email account!",
            user.getEmail(), 
            "Thank you for signing up to String Reddit" + 
            "Please click the link below to activate your account" + 
            "http://localhost:8080/api/auth/accountVerification/" + token
        ));
    }

    /**
     * 
     * @param user
     * @return
     */
    private String generateVerificationToken(User user) {
        System.out.println("✅ AuthService.generateVerificationToken()");
        String token = UUID.randomUUID().toString();
        // Create  a new Verification token 
        var verifiedToken = new VerificationToken();
        verifiedToken.setToken(token);
        verifiedToken.setUser(user);
        // Save Token
        verificatioRepo.save(verifiedToken);        
        return token;
    }
    /**
     * 
     * @param token
     * @return
     * @throws Exception
     */
    public void verifyAccount(String token) { 
        System.out.println("✅ AuthService.verifyAccount()");
        // decode the password 
        var verificationToken = verificatioRepo.findByToken(token);
        verificationToken.orElseThrow(() -> new IllegalStateException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }
    /**
     * Accesses database via userRepository
     * @param verificationToken
     */
    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        System.out.println("✅ AuthService.fetchUserAndEnable()");
        // Long userId = verificationToken.getUser().getUserId();
        // userRepository.findById(userId);
        String userName = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(userName)
            .orElseThrow(() -> new IllegalStateException("Unable to find user by username! username"));
        
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void loginUser(LoginRequest loginReq) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginReq.getUsername(),
                loginReq.getPassword()
            )
        );
    }

}

