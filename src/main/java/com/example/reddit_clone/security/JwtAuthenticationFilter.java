package com.example.reddit_clone.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    

    @Autowired
    private JwtProvider jwtProvider; 

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("✅ JwtAuthenticationFilter.doFilterInternal()");
        // Get JWT request 
        String jwtFromRequest = getJwtFromRequest(request);
        

        Boolean checkExistJwt = StringUtils.hasText(jwtFromRequest);
        // Validate the user jwt 
        if (checkExistJwt && jwtProvider.validateToken(jwtFromRequest)) { 
            
            // Retrieve user from jwt 
            String username = jwtProvider.getUsernameFromJwt(jwtFromRequest);

            // Retrieve user from db 
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            // Update username password auth token
            var auth = new UsernamePasswordAuthenticationToken(
                username, 
                null, 
                userDetails.getAuthorities());

            auth.setDetails(new WebAuthenticationDetailsSource()
                .buildDetails(request));
            
            // Set new context 
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        // Else call the filterChain 
        filterChain.doFilter(request, response);
    }

    /**
     * Helper function to get the JWT from request 
     * @param request
     * @return
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        System.out.println("✅ JwtAuthenticationFilter.getJwtFromRequest()");
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) 
            && bearerToken.startsWith("Bearer")) {
                return bearerToken.substring(7);
        }
        return bearerToken;
    }
    
}
