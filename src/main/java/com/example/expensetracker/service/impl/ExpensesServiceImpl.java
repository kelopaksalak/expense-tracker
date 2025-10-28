package com.example.expensetracker.service.impl;

import com.example.expensetracker.dto.ExpensesDto;
import com.example.expensetracker.entity.Categories;
import com.example.expensetracker.entity.Expenses;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.exception.ResourceNotFoundException;
import com.example.expensetracker.exception.TodoAPIException;
import com.example.expensetracker.repository.CategoriesRepository;
import com.example.expensetracker.repository.ExpensesRepository;
import com.example.expensetracker.service.ExpenseService;
import com.example.expensetracker.utils.ExpensesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpensesServiceImpl implements ExpenseService {

    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    private ExpensesMapper expensesMapper;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public List<ExpensesDto> getAllExpense() {
        User user = currentUserService.getCurrentUser();

        Optional<List<Expenses>> allExpenses = expensesRepository.findByUserId(user.getId());
        List<ExpensesDto> allExpensesDto = allExpenses.get().stream().map(expensesMapper::toExpensesDto).toList();
        return allExpensesDto;
    }

    @Override
    public ExpensesDto getExpense(Long id) {
        Expenses expense = expensesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense with ID " + id + " is not found!"));
        return expensesMapper.toExpensesDto(expense);
    }

    @Override
    public ExpensesDto createExpenses(ExpensesDto expensesDto) {

        User currentUser = currentUserService.getCurrentUser();

        Categories choosenCategories = categoriesRepository.findById(expensesDto.getCategoryId()).orElseThrow(() -> new TodoAPIException(HttpStatus.BAD_REQUEST, "Category with ID " + expensesDto.getCategoryId() + " is not found!"));

        Expenses newExpenses = Expenses.builder()
                .title(expensesDto.getTitle())
                .description(expensesDto.getDescription())
                .amount(expensesDto.getAmount())
                .expenseDate(expensesDto.getExpenseDate())
                .createdAt(OffsetDateTime.now())
                .updateAt(OffsetDateTime.now())
                .user(currentUser)
                .category(choosenCategories)
                .build();
        Expenses expensesSave = expensesRepository.save(newExpenses);
        return expensesMapper.toExpensesDto(expensesSave);
    }

    @Override
    public ExpensesDto updateExpenses(Long id, ExpensesDto expensesDto) {
        User currentUser = currentUserService.getCurrentUser();

        Expenses existingExpense = expensesRepository.findById(id).orElseThrow(() -> new TodoAPIException(HttpStatus.BAD_REQUEST, "Expense not found"));

        if (!existingExpense.getUser().getId().equals(currentUser.getId())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "You are not allowed to update this expense");
        }

        existingExpense.setTitle(expensesDto.getTitle());
        existingExpense.setDescription(expensesDto.getDescription());
        existingExpense.setAmount(expensesDto.getAmount());
        existingExpense.setExpenseDate(expensesDto.getExpenseDate());
        existingExpense.setUpdateAt(OffsetDateTime.now());

        Expenses updateExpense = expensesRepository.save(existingExpense);

        return expensesMapper.toExpensesDto(updateExpense);
    }

    @Override
    public void deleteExpenses(Long id) {

        User currnetUser = currentUserService.getCurrentUser();

        Expenses existingExpense = expensesRepository.findById(id).orElseThrow(() -> new TodoAPIException(HttpStatus.BAD_REQUEST, "You are not allowed to delete this expense"));

        if (!existingExpense.getUser().getId().equals(currnetUser.getId())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "You are not allowed to delete this expense");
        }

        expensesRepository.deleteById(id);
    }
}
