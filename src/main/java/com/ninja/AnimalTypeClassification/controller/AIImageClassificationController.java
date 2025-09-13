package com.ninja.AnimalTypeClassification.controller;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.services.AIService;
import com.ninja.AnimalTypeClassification.services.CloudinaryImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ai/classify")
public class AIImageClassificationController {

    private final AIService aiService;
    private final CloudinaryImageService imageService;

    @Value("${api.key}")
    private String apiKey;

    @Value("${groq.api.key}")
    private String groqApiKey;

    @PostMapping
    public ResponseEntity<List<ClassificationDetailDto>> getAnimalDetails(@RequestParam("image") MultipartFile image){
        String imageUrl;
        String path = "animal/extra";
        Map<String, Object> imageData = imageService.upload(image, path);
        imageUrl = (String) imageData.get("secure_url");

        List<ClassificationDetailDto> details = aiService.classifyImage(imageUrl, apiKey, groqApiKey);

        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @GetMapping("/{species}/{breed}")
    public Map<String, String> groq(@PathVariable("species") String species, @PathVariable("breed") String breed){
        return aiService.getImageFromGroq(breed, species, groqApiKey);
    }
}
