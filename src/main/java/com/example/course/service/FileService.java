package com.example.course.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void saveFile(MultipartFile file, String imagePath);

    void deleteFile(String oldImgName);
}
