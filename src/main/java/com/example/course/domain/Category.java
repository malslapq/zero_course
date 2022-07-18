package com.example.course.domain;


import com.example.course.type.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String categoryName;
    private int sortValue;
    @Enumerated(EnumType.STRING)
    private CategoryStatus categoryStatus;

    public void changeCategory(String categoryName, int sortValue, CategoryStatus categoryStatus) {
        this.categoryName = categoryName;
        this.sortValue = sortValue;
        this.categoryStatus = categoryStatus;
    }

}
