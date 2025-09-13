package com.ninja.AnimalTypeClassification.services.impl;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.services.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final WebClient.Builder webClientBuilder;

    @Override
    public List<ClassificationDetailDto> classifyImage(String imageUrl, String apiKey) {
        String url = "https://serverless.roboflow.com/infer/workflows/project-odijn/custom-workflow-2";

        Map<String, Object> body = Map.of(
                "api_key", apiKey,
                "inputs", Map.of("image", Map.of("type", "url", "value", imageUrl))
        );

        Map<String, Object> response = webClientBuilder.build()
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        List<ClassificationDetailDto> result = new ArrayList<>();

        if (response != null && response.containsKey("outputs")) {
            List<Map<String, Object>> outputs = (List<Map<String, Object>>) response.get("outputs");

            for (Map<String, Object> output : outputs) {
                Map<String, Object> predictions = (Map<String, Object>) output.get("predictions");

                if (predictions != null) {
                    String breed = (String) predictions.get("top");
                    Double confidence = ((Number) predictions.get("confidence")).doubleValue();

                    result.add(new ClassificationDetailDto("Breed", breed, confidence * 100));
                }
            }
        }

        return result;
    }
}
