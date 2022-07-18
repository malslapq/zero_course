package com.example.course.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService{

    private final String path = "D:/zero_course/src/main/resources/static/image/";

    @Override
    public void deleteFile(String oldImgName) {
        File deleteFile = new File(path, oldImgName);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }

    @Override
    public void saveFile(MultipartFile file, String imageName) {

        File saveFile = new File(path, imageName);
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
