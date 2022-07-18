package com.example.course.model.banner;

import com.example.course.domain.Banner;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerDto {

    private Long id;
    private String name;
    private String imageName;
    private int sortValue;
    private boolean status;
    private LocalDateTime createDate;

    public static BannerDto fromEntity(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .name(banner.getName())
                .imageName(banner.getImageName())
                .sortValue(banner.getSortValue())
                .createDate(banner.getCreateDate())
                .status(banner.isStatus())
                .build();
    }


}
