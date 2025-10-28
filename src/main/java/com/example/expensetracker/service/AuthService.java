package com.example.expensetracker.service;

import com.example.expensetracker.dto.JwtAuthResponse;
import com.example.expensetracker.dto.LoginDto;
import com.example.expensetracker.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto loginDto);

}
