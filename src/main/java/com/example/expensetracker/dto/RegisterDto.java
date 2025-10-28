package com.example.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String password;
}
