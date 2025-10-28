package com.example.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpensesDto {
    private Long id;
    private BigDecimal amount;
    private String title;
    private String description;
    private Long categoryId;
    private OffsetDateTime expenseDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
