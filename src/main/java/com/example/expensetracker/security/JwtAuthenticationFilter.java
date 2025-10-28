package com.example.expensetracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Injected JwtTokenProvider for handling JWT operations
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Injected UserDetailsService for loading user details
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Get JWT token from the HTTP request
        String token = getTokenFromRequest(request);

        // Validate the token
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            // Extract username from the valid token
            String username = jwtTokenProvider.getUsername(token);

            // Load user details based on the extracted username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Create an authentication token with user details
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities() // Get user authorities (roles)
            );

            // Set additional details for the authentication
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set the authentication object in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // Continue the filter chain (invoke the next filter)
        filterChain.doFilter(request, response);
    }

    // Helper method to extract the JWT token from the Authorization header
    private String getTokenFromRequest(HttpServletRequest request){
        // Retrieve the Authorization header
        String bearerToken = request.getHeader("Authorization");

        // Check if the header is valid and starts with "Bearer "
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            // Return the token (excluding the "Bearer " prefix)
            return bearerToken.substring(7, bearerToken.length());
        }

        // Return null if no valid token is found
        return null;
    }
}
