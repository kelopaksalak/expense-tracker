package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpensesDto;

import java.util.List;

public interface ExpenseService {

    List<ExpensesDto> getAllExpense();

    ExpensesDto getExpense(Long id);

    ExpensesDto createExpenses(ExpensesDto expensesDto);

    ExpensesDto updateExpenses(Long id, ExpensesDto expensesDto);

    void deleteExpenses(Long id);
}
