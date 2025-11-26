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
public class CategoriesDto {
    private Long id;
    private String name;
    private String type;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Long idExpense;
}
