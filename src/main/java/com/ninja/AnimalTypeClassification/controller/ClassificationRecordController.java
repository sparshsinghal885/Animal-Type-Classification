package com.ninja.AnimalTypeClassification.controller;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.entity.ClassificationRecord;
import com.ninja.AnimalTypeClassification.services.AIService;
import com.ninja.AnimalTypeClassification.services.ClassificationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals/{animalId}/records")
@RequiredArgsConstructor
public class ClassificationRecordController {

    private final AIService aiService;
    private final ClassificationRecordService recordService;

    // Create record using AI classification
    @PostMapping("/classify")
    public ResponseEntity<ClassificationRecord> classifyAnimal(
            @PathVariable Long animalId,
            @RequestParam String imageUrl) {

        List<ClassificationDetailDto> details = aiService.classifyImage(imageUrl);

        ClassificationRecord record = recordService.createRecord(animalId, details);

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
