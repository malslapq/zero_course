package com.example.course.model.course;


import com.example.course.domain.Course;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDto {

    private Long id;
    private Long categoryId;
    private String keyword;
    private String imagePath;
    private String subject;
    private String summary;
    private String contents;
    private Long price;
    private Long salePrice;
    private LocalDateTime saleEndDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


    public static CourseDto fromEntity(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .categoryId(course.getCategoryId())
                .keyword(course.getKeyword())
                .imagePath(course.getImagePath())
                .subject(course.getSubject())
                .summary(course.getSummary())
                .contents(course.getContents())
                .price(course.getPrice())
                .salePrice(course.getSalePrice())
                .saleEndDate(course.getSaleEndDate())
                .createdDate(course.getCreatedDate())
                .updatedDate(course.getUpdatedDate())
                .build();
    }

}
