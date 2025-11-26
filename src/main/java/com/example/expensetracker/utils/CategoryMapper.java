package com.example.expensetracker.utils;

import com.example.expensetracker.dto.CategoriesDto;
import com.example.expensetracker.entity.Categories;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoriesDto toCategoriesDto(Categories categories);

    Categories toCategories(CategoriesDto categoriesDto);

}
