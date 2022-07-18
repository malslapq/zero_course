package com.example.course.service;

import com.example.course.domain.Course;
import com.example.course.exception.CourseException;
import com.example.course.model.course.AddCourse;
import com.example.course.model.course.CourseDto;
import com.example.course.repository.CourseRepository;
import com.example.course.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public LocalDateTime getLocalDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd " +
                "HH:mm:ss");
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    @Override
    public void modify(Long id, AddCourse.Request request) {
        Course course = courseRepository.findById(id).orElseThrow(() ->
                new CourseException(ErrorCode.COURSE_NOT_FOUND));
        course.updateCourse(request);
        courseRepository.save(course);
    }

    @Override
    public CourseDto select(Long id) {
        return CourseDto.fromEntity(courseRepository.findById(id).orElseThrow(() ->
                new CourseException(ErrorCode.COURSE_NOT_FOUND)));
    }

    @Override
    public void insert(AddCourse.Request request, Long categoryId) {

        if (courseRepository.existsBySubject(request.getSubject())) {
            throw new CourseException(ErrorCode.USED_COURSE_SUBJECT);
        }
        courseRepository.save(Course.builder()
                .categoryId(categoryId)
                .keyword(request.getKeyword())
                .imagePath(request.getImagePath())
                .subject(request.getSubject())
                .summary(request.getSummary())
                .contents(request.getContents())
                .price(request.getPrice())
                .salePrice(request.getSalePrice())
                .saleEndDate(getLocalDate(request.getSaleEndDate()))
                .build());
    }

    @Override
    public Page<CourseDto> selectAll(String searchType, String searchValue, Pageable pageable) {
        return courseRepository.findAll(pageable).map(CourseDto::fromEntity);
    }

}