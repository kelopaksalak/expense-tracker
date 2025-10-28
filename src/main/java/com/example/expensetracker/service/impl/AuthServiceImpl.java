package com.example.expensetracker.service.impl;

import com.example.expensetracker.dto.JwtAuthResponse;
import com.example.expensetracker.dto.LoginDto;
import com.example.expensetracker.dto.RegisterDto;
import com.example.expensetracker.entity.Role;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.exception.TodoAPIException;
import com.example.expensetracker.repository.RoleRepository;
import com.example.expensetracker.repository.UserRepository;
import com.example.expensetracker.security.JwtTokenProvider;
import com.example.expensetracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setCreatedAt(OffsetDateTime.now());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Optional<User> findUser = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());

        System.out.println(loginDto.getUsernameOrEmail());

        if (findUser.isEmpty()){
            throw new TodoAPIException(HttpStatus.NOT_FOUND, "Username/Email is not found!");
        }

        if(!passwordValidation(loginDto.getPassword(), findUser.get().getPassword())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Password is not correct");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        String role = null;
        if(findUser.isPresent()){
            User currentUser = findUser.get();
            Optional<Role> optionalRole = currentUser.getRoles().stream().findFirst();

            if(optionalRole.isPresent()){
                Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);


        return jwtAuthResponse;
    }


    private boolean passwordValidation(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
