package com.ninja.AnimalTypeClassification.controller;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.entity.ClassificationRecord;
import com.ninja.AnimalTypeClassification.services.AIService;
import com.ninja.AnimalTypeClassification.services.ClassificationRecordService;
import com.ninja.AnimalTypeClassification.services.CloudinaryImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/animals/{animalId}/records")
@RequiredArgsConstructor
public class ClassificationRecordController {

    private final AIService aiService;
    private final ClassificationRecordService recordService;
    private final CloudinaryImageService imageService;

    @PostMapping(value = "/classify",consumes = "multipart/form-data")
    public ResponseEntity<ClassificationRecord> classifyAnimal(
            @PathVariable Long animalId,
            @RequestParam("images") MultipartFile[] images) {

        List<ClassificationDetailDto> details = aiService.classifyImage(images);

        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : images) {
            Map<String, Object> imageData = imageService.upload(file, "animal/" + animalId);
            imageUrls.add((String) imageData.get("secure_url"));
        }

        // 3. Create classification record with image URLs
        ClassificationRecord record = recordService.createRecord(animalId, details, imageUrls);
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
