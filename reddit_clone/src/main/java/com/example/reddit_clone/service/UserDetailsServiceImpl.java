package com.example.reddit_clone.service;

import java.util.Collection;
import java.util.Optional;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reddit_clone.models.UserObject;
import com.example.reddit_clone.repository.UserRepository;

import lombok.AllArgsConstructor;
import static java.util.Collections.singletonList;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserObject> userOption = userRepository.findByUsername(username);

        var user = userOption
            .orElseThrow(() -> new UsernameNotFoundException("Unable to retrieve the user details"));
        
        // This is provided by the Spring Framework 
        return new org.springframework.security.core.userdetails.User(user.getUsername(), 
            user.getPassword(), 
            user.isEnabled(), 
            true, 
            true, 
            true, 
            getAuthorities("USER")
        );
    }

    /**
     * This will return the Object collections 
     * @param role
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
 
}
