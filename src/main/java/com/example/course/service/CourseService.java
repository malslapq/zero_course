package com.example.course.service;

import com.example.course.model.course.AddCourse;
import com.example.course.model.course.CourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    void insert(AddCourse.Request request, Long categoryId);

    Page<CourseDto> selectAll(String searchType, String searchValue, Pageable pageable);

    CourseDto select(Long id);

    void modify(Long id, AddCourse.Request request);
}
