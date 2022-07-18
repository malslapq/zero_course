package com.example.course.repository;


import com.example.course.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryName(String categoryName);

    List<Category> findAllByOrderBySortValueDesc();

}
