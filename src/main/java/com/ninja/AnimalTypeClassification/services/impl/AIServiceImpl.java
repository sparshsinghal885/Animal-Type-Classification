package com.ninja.AnimalTypeClassification.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.services.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private static final Set<String> cowBreeds = new HashSet<>(Arrays.asList(
            "Alambadi", "Amritmahal", "Ayrshire", "Dangi", "Deoni", "Gir", "Guernsey",
            "Hallikar", "Hariana", "Holstein_Friesian", "Jersey", "Kangayam", "Kankrej",
            "Kasargod", "Kenkatha", "Kherigarh", "Khillari", "Krishna_Valley", "Malnad_gidda",
            "Nagori", "Nimari", "Ongole", "Pulikulam", "Rathi", "Red_Dane", "Red_Sindhi",
            "Sahiwal", "Tharparkar", "Umblachery", "Vechur"
    ));
    private static final Set<String> buffaloBreeds = new HashSet<>(Arrays.asList(
            "Bargur", "Banni", "Bhadawari", "Brown_Swiss", "Jaffrabadi", "Mehsana",
            "Murrah", "Nagpuri", "Nili_Ravi", "Surti", "Surti buffalo", "Toda"
    ));

    private final WebClient.Builder webClientBuilder;

    @Value("${groq.api.key}")
    private String groqApi;

    private static final String GROQ_BASE_URL = "https://api.groq.com/openai/v1";
    private static final String MODEL = "meta-llama/llama-4-scout-17b-16e-instruct";

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
                    result.add(new ClassificationDetailDto("Species", getSpecies(breed), 100.0));
                }
            }
        }

        List<ClassificationDetailDto> fromGroq = getInfoFromGroq(result.get(0).getValue(), result.get(1).getValue());
        result.addAll(fromGroq);
        return result;
    }

    @Override
    public List<ClassificationDetailDto> getInfoFromGroq(String breed, String species) {
        try {
            // Build request body
            Map<String, Object> requestBody = Map.of(
                    "model", "meta-llama/llama-4-scout-17b-16e-instruct",
                    "messages", List.of(
                            Map.of("role", "user", "content",
                                    "Return ONLY valid JSON with keys: color, horn shape and size, visible characteristics, avg height, body length, heart girth avg. " +
                                            "Describe a " + breed + " of species " + species + ". No explanations, no markdown, just JSON.")
                    )
            );

            // Call Groq API
            String response = webClientBuilder.build()
                    .post()
                    .uri("https://api.groq.com/openai/v1/chat/completions")
                    .header("Authorization", "Bearer " + groqApi) // inject with @Value
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("response = " + response);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            // Get the content string
            String content = root.path("choices").get(0).path("message").path("content").asText();

            // Strip ```json ... ``` wrappers if present
            content = content.replaceAll("(?s)```json|```", "").trim();

            // Now parse the clean JSON
            JsonNode details = mapper.readTree(content);

            List<ClassificationDetailDto> result = new ArrayList<>();
            details.fields().forEachRemaining(entry -> {
                result.add(new ClassificationDetailDto(entry.getKey(), entry.getValue().asText(), 100));
            });

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Groq response: " + e.getMessage(), e);
        }
    }


    public static String getSpecies(String breed) {
        if (cowBreeds.contains(breed)) {
            return "Cow";
        } else if (buffaloBreeds.contains(breed)) {
            return "Buffalo";
        } else {
            return "Unknown Species";
        }
    }
}
