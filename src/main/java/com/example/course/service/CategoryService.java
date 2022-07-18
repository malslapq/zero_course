package com.example.course.service;

import com.example.course.model.category.CategoryDto;
import com.example.course.type.CategoryStatus;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> selectAll();

    void add(String categoryName);

    void update(Long id, String categoryName, int sortValue, CategoryStatus categoryStatus);

    void delete(Long id);

}
