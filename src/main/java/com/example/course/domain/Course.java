package com.example.course.domain;

import com.example.course.model.course.AddCourse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long categoryId;
    private String keyword;
    private String imagePath;
    private String subject;
    @Column(length = 1000)
    private String summary;
    @Lob
    private String contents;
    private Long price;
    private Long salePrice;
    private LocalDateTime saleEndDate;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public void updateCourse(AddCourse.Request request) {
        this.categoryId = request.getCategoryId();
        this.keyword = request.getKeyword();
        this.imagePath = request.getImagePath();
        this.subject = request.getSubject();
        this.summary = request.getSummary();
        this.contents = request.getContents();
        this.price = request.getPrice();
        this.salePrice = request.getSalePrice();
        this.saleEndDate = LocalDateTime.parse(request.getSaleEndDate());
    }

}
