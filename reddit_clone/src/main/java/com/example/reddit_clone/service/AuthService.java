package com.example.reddit_clone.service;

import java.time.Instant;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reddit_clone.dto.AuthenticationResponse;
import com.example.reddit_clone.dto.LoginRequest;
import com.example.reddit_clone.dto.RefreshTokenRequest;
import com.example.reddit_clone.dto.RegisterRequest;
import com.example.reddit_clone.models.NotificationEmail;
// import com.example.reddit_clone.models.NotificationEmail;
import com.example.reddit_clone.models.UserObject;
import com.example.reddit_clone.models.VerificationToken;
import com.example.reddit_clone.repository.UserRepository;
import com.example.reddit_clone.repository.VerificationRepo;
import com.example.reddit_clone.security.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    /*
     * Find me the matching object in the SecurityConfig 
        @Autowired
     */
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationRepo verificatioRepo;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    // private final MailService mailService;
    private final KafkaProducerService  kafkaProducerService;

    /** 
     * @Transactional 
     * Spring creates proxies for all the classes annotated with . 
     * either on the class or on any of the methods
     */
    public void signUp(RegisterRequest req) { 
        System.out.println("✅ AuthService.signUp()");
        
        // Create a new User 
        UserObject user = new UserObject();
            user.setUsername(req.getUsername());
            user.setEmail(req.getEmail());
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setCreatedAt(Instant.now());
            user.setEnabled(false);

        // Save User Details inside the Database
        System.out.println("😎 Inserting into Database");
        userRepository.save(user);

        // Generate Verification Token
        var token = generateVerificationToken(user);
        System.out.println(token);
        
        NotificationEmail newMessage = new NotificationEmail(
                "Spring Boot -- Activate your email account!",
                user.getEmail(), 
                "Thank you for signing up to String Reddit" + 
                "Please click the link below to activate your account" + 
                "http://localhost:8080/api/auth/accountVerification/" + token
        );
        
        // Send Activation Mail with the activitation link 
        // Send message over to Kafka 
        System.out.println("Sending Message to MailService");
        kafkaProducerService.sendMessage("notificationEmail", newMessage);

    }

    /**
     * 
     * @param user
     * @return
     */
    private String generateVerificationToken(UserObject user) {
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
        UserObject user = userRepository.findByUsername(userName)
            .orElseThrow(() -> new IllegalStateException("Unable to find user by username! username"));
        
        user.setEnabled(true);
        userRepository.save(user);
    }

    /**
     * Login the user details and set the new context 
     * 
     * @param loginReq
     */
    public AuthenticationResponse loginUser(LoginRequest loginReq) {
        System.out.println("✅ AuthService.loginUser()");
        var authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginReq.getUsername(),
                loginReq.getPassword()
            )
        );
        //  Set the security context
        SecurityContextHolder
            .getContext()
            .setAuthentication(authenticate);
        
        var jwt = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
            .authentiticatedToken(jwt)
            .username(loginReq.getUsername())
            .refreshToken(refreshTokenService.generateRefreshToken().getToken())
            .expiresAt(Instant.now().plusMillis(jwtProvider.getExpirationTime()))
            .build();
    }
    
    /**
     * 
     * @return
     */
    public UserObject getCurrentUser() {
        System.out.println("✅ AuthService.getCurrentUser()");
        // Get the current security context
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        return userRepository
            .findByUsername(principal.getUsername())
            .orElseThrow(() -> new IllegalStateException("Unable to get the current context for this user"));
    }

    /**
     * Validate Refresh token first
     * Generate token based on username
     * Send Authentication Response to the user
     * @param req RefreshTokenRequest
     * @return AuthenticationResponse 
     */
    public AuthenticationResponse refreshToken(@Valid RefreshTokenRequest req) {
        
        refreshTokenService.validateRefreshToken(req.getRefreshtoken());
        var token = jwtProvider.generateTokenWithUserName(req.getUsername());
        return AuthenticationResponse.builder()
            .authentiticatedToken(token)
            .username(req.getUsername())
            .refreshToken(req.getRefreshtoken())
            .expiresAt(Instant.now().plusMillis(jwtProvider.getExpirationTime()))
            .build(); 
    }
}

