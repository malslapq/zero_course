package com.example.course.service;

import com.example.course.model.banner.BannerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {
    Page<BannerDto> selectAll(Pageable pageable);

    void insert(String name, String imagePath, int sortValue, boolean status);

    BannerDto select(Long id);

    String modify(Long id, String name, int sortValue, boolean status, String imageName);
}
