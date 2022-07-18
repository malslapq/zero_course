package com.example.course.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String imageName;
    private int sortValue;
    private boolean status;
    @CreatedDate
    private LocalDateTime createDate;

    public void modifyBanner(String name, String imageName, int sortValue, boolean status) {
        this.name = name;
        this.imageName = imageName;
        this.sortValue = sortValue;
        this.status = status;
    }

}
