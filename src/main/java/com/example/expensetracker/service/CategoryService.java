package com.example.expensetracker.service;

import com.example.expensetracker.dto.CategoriesDto;

import java.util.List;

public interface CategoryService {

    List<CategoriesDto> getAllCategories();

    CategoriesDto getCategory(Long id);

    CategoriesDto createCategory(CategoriesDto categoriesDto);

    CategoriesDto updateCategory(Long id, CategoriesDto categoriesDto);

    void deleteCategory(Long id);
}
