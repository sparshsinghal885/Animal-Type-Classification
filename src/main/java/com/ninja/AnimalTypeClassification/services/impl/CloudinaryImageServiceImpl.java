package com.ninja.AnimalTypeClassification.services.impl;

import com.cloudinary.Cloudinary;
import com.ninja.AnimalTypeClassification.services.CloudinaryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryImageServiceImpl implements CloudinaryImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map<String, Object> upload(MultipartFile file, String folderPath) {
        try {
            return cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of("folder", folderPath)
            );
        } catch (IOException e) {
            throw new RuntimeException("Image uploading failed: " + e.getMessage());
        }
    }
}
