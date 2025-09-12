package com.ninja.AnimalTypeClassification.controller;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.services.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/classify")
public class AIImageClassification {

    private final AIService aiService;

    @GetMapping
    public ResponseEntity<List<ClassificationDetailDto>> getAnimalDetails(@RequestParam("images") MultipartFile[] images){
        List<ClassificationDetailDto> details = aiService.classifyImage(images);

        return new ResponseEntity<>(details, HttpStatus.OK);
    }
}
