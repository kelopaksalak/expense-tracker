package com.example.expensetracker.controller;

import com.example.expensetracker.dto.CategoriesDto;
import com.example.expensetracker.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/category")
@CrossOrigin("*")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoriesDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CategoriesDto> getCategory(@PathVariable Long id){
        CategoriesDto categoriesDto = categoryService.getCategory(id);
        return ResponseEntity.ok(categoriesDto);
    }

    @PostMapping("/create")
    public ResponseEntity<String> addNewCategory(@RequestBody CategoriesDto categoriesDto){
        CategoriesDto createCategory = categoryService.createCategory(categoriesDto);
        return ResponseEntity.ok("Successfully create new category");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateExistingCategory(@PathVariable Long id, @RequestBody CategoriesDto categoriesDto){
        CategoriesDto updateCategory = categoryService.updateCategory(id, categoriesDto);
        return ResponseEntity.ok("Successfully update category with ID " + id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExistingCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Successfully delete category with ID " + id);
    }
}
