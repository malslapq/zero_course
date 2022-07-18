package com.example.course.service;


import com.example.course.domain.Category;
import com.example.course.exception.CategoryException;
import com.example.course.model.category.CategoryDto;
import com.example.course.repository.CategoryRepository;
import com.example.course.type.CategoryStatus;
import com.example.course.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(()
                -> new CategoryException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Override
    public void add(String categoryName) {

        if (categoryRepository.existsByCategoryName(categoryName)) {
            throw new CategoryException(ErrorCode.USED_CATEGORY_NAME);
        }

        categoryRepository.save(Category.builder()
                .categoryName(categoryName)
                .categoryStatus(CategoryStatus.USE)
                .sortValue(0)
                .build());
    }

    @Override
    public void update(Long id, String categoryName, int sortValue, CategoryStatus categoryStatus) {
        Category category = getCategory(id);
        category.changeCategory(categoryName, sortValue, categoryStatus);
        categoryRepository.save(category);
    }


    @Override
    public void delete(Long id) {
        Category category = getCategory(id);
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> selectAll() {
        return categoryRepository.findAllByOrderBySortValueDesc()
                .stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }
}
