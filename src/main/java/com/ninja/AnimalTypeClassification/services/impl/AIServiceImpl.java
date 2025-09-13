package com.ninja.AnimalTypeClassification.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.services.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final WebClient.Builder webClientBuilder;

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

    @Override
    public List<ClassificationDetailDto> classifyImage(String imageUrl, String apiKey, String grokApiKey) {
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
                    String species = getSpecies(breed);
                    result.add(new ClassificationDetailDto("Breed", breed, confidence * 100));
                    result.add(new ClassificationDetailDto("Species", species, 100.0));
                }
            }
        }

        if(result.get(1).getValue().equalsIgnoreCase("Unknown Species")){
            return result;
        }

        Map<String, String> res = getImageFromGroq(result.get(0).getValue(), result.get(1).getValue(), grokApiKey);

        if (res.isEmpty()) {
            System.out.println("Groq returned empty map!");
        } else {
            for (Map.Entry<String, String> entry : res.entrySet()) {
                result.add(new ClassificationDetailDto(entry.getKey(), entry.getValue(), 100.0));
            }
        }

        return result;
    }

    @Override
    public Map<String, String> getImageFromGroq(String breed, String species, String apiKey) {
        WebClient webClient = webClientBuilder
                .baseUrl("https://api.groq.com/openai/v1")
                .defaultHeader("Authorization", "Bearer "+ apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();

        String prompt = """
        Provide structured information about the %s breed of %s, including:
        - Horn Shape
        - Horn Size
        - Average Height
        - Average Body Length
        - Heart Girth
        - Milk Yield per Lactation

        Return the response as pure JSON only, without markdown, explanation, or code blocks.
        """.formatted(breed, species);

        // request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "meta-llama/llama-4-scout-17b-16e-instruct");
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", prompt));
        requestBody.put("messages", messages);

        try {
            Map resp = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (resp == null || resp.get("choices") == null) {
                return Map.of("error", "No response!");
            }

            List<Map<String, Object>> choices = (List<Map<String, Object>>) resp.get("choices");
            if (choices.isEmpty()) {
                return Map.of("error", "No response!");
            }

            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            String content = (message != null && message.get("content") != null)
                    ? message.get("content").toString().trim()
                    : "";

            if (content.startsWith("```")) {
                int first = content.indexOf('{');
                int last = content.lastIndexOf('}');
                if (first != -1 && last != -1 && last > first) {
                    content = content.substring(first, last + 1);
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(content);

            Map<String, String> result = new LinkedHashMap<>();
            root.fields().forEachRemaining(entry -> result.put(entry.getKey(), entry.getValue().asText()));

            return result;

        } catch (Exception e) {
            return Map.of("error", e.getMessage());
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
