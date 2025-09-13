package com.ninja.AnimalTypeClassification.controller;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.services.AIService;
import com.ninja.AnimalTypeClassification.services.CloudinaryImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ai/classify")
public class AIImageClassification {

    private final AIService aiService;
    private final CloudinaryImageService imageService;

    @Value("${api.key}")
    private String apiKey;

    @GetMapping
    public ResponseEntity<List<ClassificationDetailDto>> getAnimalDetails(@RequestParam("images") MultipartFile image){
        String imageUrl;
        String path = "animal/extra";
        Map<String, Object> imageData = imageService.upload(image, path);
        imageUrl = (String) imageData.get("secure_url");

        List<ClassificationDetailDto> details = aiService.classifyImage(imageUrl, apiKey);

        return new ResponseEntity<>(details, HttpStatus.OK);
    }
}
