package com.ninja.AnimalTypeClassification.services.impl;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.services.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AIServiceImpl implements AIService {

    // Mock implementation: normally you’d call an external AI API
//    private final WebClient webClient;
//
//    public AIServiceImpl(WebClient.Builder builder) {
//        this.webClient = builder.baseUrl("http://friend-ai-service.com/api").build();
//    }

    public List<ClassificationDetailDto> classifyImage(MultipartFile[] images) {

        return List.of(
                new ClassificationDetailDto("Species", "Buffalow" ,85.0),
                new ClassificationDetailDto("Breed", "Murrah" ,80.0),
                new ClassificationDetailDto("Color", "Black" ,85.0),
                new ClassificationDetailDto("Pattern", "Simple",72.5),
                new ClassificationDetailDto("Health", "Good",90.0)
        );

    }
}
