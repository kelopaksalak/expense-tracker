package com.example.expensetracker.service.impl;

import com.example.expensetracker.dto.CategoriesDto;
import com.example.expensetracker.entity.Categories;
import com.example.expensetracker.exception.ResourceNotFoundException;
import com.example.expensetracker.repository.CategoriesRepository;
import com.example.expensetracker.service.CategoryService;
import com.example.expensetracker.utils.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoriesDto> getAllCategories() {
        List<Categories> categories = categoriesRepository.findAll();
        List<CategoriesDto> categoriesDto = categories.stream().map(categoryMapper::toCategoriesDto).toList();
        return categoriesDto;
    }

    @Override
    public CategoriesDto getCategory(Long id) {
        Categories categories = categoriesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with ID " + id + " is not found!"));
        return categoryMapper.toCategoriesDto(categories);
    }

    @Override
    public CategoriesDto createCategory(CategoriesDto categoriesDto) {
        Categories categories = Categories.builder()
                .name(categoriesDto.getName())
                .type(categoriesDto.getType())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        Categories saved = categoriesRepository.save(categories);
        return categoryMapper.toCategoriesDto(saved);
    }

    @Override
    public CategoriesDto updateCategory(Long id, CategoriesDto categoriesDto) {
        Categories categories = categoriesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with ID " + id + " is not found!"));
        if (categoriesDto.getName() != null) {
            categories.setName(categoriesDto.getName());
        }
        if (categoriesDto.getType() != null) {
            categories.setType(categoriesDto.getType());
        }
        categories.setUpdatedAt(OffsetDateTime.now());

        Categories saveCategories = categoriesRepository.save(categories);
        return categoryMapper.toCategoriesDto(saveCategories);
    }

    @Override
    public void deleteCategory(Long id) {
        categoriesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with ID " + id + " is not found!"));
        categoriesRepository.deleteById(id);
    }
}
