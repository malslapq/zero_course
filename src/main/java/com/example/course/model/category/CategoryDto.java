package com.example.course.model.category;

import com.example.course.domain.Category;
import com.example.course.type.CategoryStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    private String categoryName;
    private int sortValue;
    private CategoryStatus categoryStatus;

    public static CategoryDto fromEntity(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .sortValue(category.getSortValue())
                .categoryStatus(category.getCategoryStatus())
                .build();
    }

}
