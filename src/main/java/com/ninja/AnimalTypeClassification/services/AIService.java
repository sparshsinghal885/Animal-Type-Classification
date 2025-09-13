package com.ninja.AnimalTypeClassification.services;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AIService {
    public List<ClassificationDetailDto> classifyImage(String imageUrl, String apiKey, String grokApiKey);

    public Map<String, String> getImageFromGroq(String breed, String species, String apiKey);
}
