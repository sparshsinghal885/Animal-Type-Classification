package com.ninja.AnimalTypeClassification.controller;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.entity.ClassificationRecord;
import com.ninja.AnimalTypeClassification.services.AIService;
import com.ninja.AnimalTypeClassification.services.ClassificationRecordService;
import com.ninja.AnimalTypeClassification.services.CloudinaryImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/animals/{animalId}/records")
@RequiredArgsConstructor
public class ClassificationRecordController {

    private final AIService aiService;
    private final ClassificationRecordService recordService;
    private final CloudinaryImageService imageService;

    @Value("${api.key}")
    private String apiKey;

    @Value("${groq.api.key}")
    private String groqApiKey;

    @PostMapping(value = "/classify",consumes = "multipart/form-data")
    public ResponseEntity<ClassificationRecord> classifyAnimal(
            @PathVariable Long animalId,
            @RequestParam("image") MultipartFile image) {

        String imageUrl;
        Map<String, Object> imageData = imageService.upload(image, "animal/" + animalId);
        imageUrl = (String) imageData.get("secure_url");

        List<ClassificationDetailDto> details = aiService.classifyImage(imageUrl, apiKey, groqApiKey);

        System.out.println("details = " + details);
        ClassificationRecord record = recordService.createRecord(animalId, details, imageUrl);
        return ResponseEntity.ok(record);
    }


    @GetMapping
    public ResponseEntity<List<ClassificationRecord>> getByAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(recordService.getByAnimalId(animalId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassificationRecord> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
