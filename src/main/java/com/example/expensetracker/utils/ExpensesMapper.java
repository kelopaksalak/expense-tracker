package com.example.expensetracker.utils;

import com.example.expensetracker.dto.ExpensesDto;
import com.example.expensetracker.entity.Expenses;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpensesMapper {

    ExpensesDto toExpensesDto(Expenses expenses);

    Expenses toExpenses(ExpensesDto expensesDto);
}
