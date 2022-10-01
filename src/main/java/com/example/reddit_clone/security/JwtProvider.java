package com.example.reddit_clone.security;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {
    
    private KeyStore keyStore;

    /**
     * Set the initial keyStore for JWT from a private key 
     */
    @PostConstruct
    public void init() { 
        System.out.println("✅ JwtProvider.init()");
        try {   
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass()
                .getResourceAsStream("app.key");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException| NoSuchAlgorithmException  | java.io.IOException e) {
            throw new IllegalStateException("❌❌ Exception occurred while keystore ");
        }
    }

    /**
     * Generates the JWT token 
     * @param auth
     * @return
     */
    public String generateToken(Authentication auth) { 
        System.out.println("✅ JwtProvider.generateToken()");
        User principal = (User) auth.getPrincipal();
        return Jwts.builder()
            .setSubject(principal.getUsername())
            .signWith(getPrivateKey())
            .compact();
    }
    /**
     * 
     * @return
     */
    private PrivateKey getPrivateKey() {
        System.out.println("✅ JwtProvider.getPrivateKey()");
        try {
            return (PrivateKey) keyStore.getKey("spring-blog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            throw new IllegalStateException("Exception occurred while retrieving public key from keystore");        
        }
    } 
    /**
     * Validate the token
     * Generate the public key
     * @param jwt
     * @return
     */
    public boolean validateToken(String jwt) { 
        System.out.println("✅ JwtProvider.validateToken()");
        Jwts.parserBuilder()
            .setSigningKey(getPublicKey())
            .build()
            .parseClaimsJws(jwt);
        return true;
    }

    /**
     * Returns the certificate with the current alias
     * @return
     */
    private PublicKey getPublicKey() {
        try { 
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) { 
            throw new IllegalStateException("Exception occurred while retrieving public key!");
        }
    }

    /**
     * Access the username from the token using the public key
     * @param token
     * @return
     */
    public String getUsernameFromJwt(String token) { 
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(getPublicKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }
}
