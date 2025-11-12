package com.example.expensetracker.controller;

import com.example.expensetracker.constant.SwaggerTag.EXPENSE;
import com.example.expensetracker.dto.ExpensesDto;
import com.example.expensetracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
@CrossOrigin("*")
@Tag(name = EXPENSE.NAME, description = EXPENSE.DESCRIPTION)
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ExpensesDto>> getExpenseAll(){
        List<ExpensesDto> allExpense = expenseService.getAllExpense();
        return ResponseEntity.ok(allExpense);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ExpensesDto> getExpense(@PathVariable Long id){
        ExpensesDto expense = expenseService.getExpense(id);
        return ResponseEntity.ok(expense);
    }

    @PostMapping("/create")
    public ResponseEntity<String> addNewExpense(@RequestBody ExpensesDto expensesDto){
        ExpensesDto newExpense = expenseService.createExpenses(expensesDto);
        return ResponseEntity.ok("Expense data saved in database");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateExistingExpense(@PathVariable Long id, @RequestBody ExpensesDto expensesDto){
        ExpensesDto existingExpense = expenseService.updateExpenses(id, expensesDto);
        return ResponseEntity.ok("Expense with ID " + id + " successfully updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExistingExpense(@PathVariable Long id){
        expenseService.deleteExpenses(id);
        return ResponseEntity.ok("Expense with ID " + id + " successfully deleted");
    }

}
