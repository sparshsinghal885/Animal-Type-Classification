package com.ninja.AnimalTypeClassification.services;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AIService {
    public List<ClassificationDetailDto> classifyImage(String imageUrl, String apiKey);
}
