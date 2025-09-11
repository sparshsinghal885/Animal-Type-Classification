package com.ninja.AnimalTypeClassification.services.impl;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.services.AIService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIServiceImpl implements AIService {
    // Mock implementation: normally you’d call an external AI API
    public List<ClassificationDetailDto> classifyImage(String imageUrl) {
        return List.of(
                new ClassificationDetailDto("Color", 85.0),
                new ClassificationDetailDto("Pattern", 72.5),
                new ClassificationDetailDto("Health", 90.0)
        );
    }
}
