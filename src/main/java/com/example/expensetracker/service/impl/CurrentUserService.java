package com.example.expensetracker.service.impl;

import com.example.expensetracker.entity.User;
import com.example.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userNameOrEmail = authentication.getName();

        return userRepository.findByUsernameOrEmail(userNameOrEmail, userNameOrEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
