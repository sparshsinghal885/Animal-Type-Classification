package com.ninja.AnimalTypeClassification.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassificationDetailDto {
    @NotBlank(message = "Trait name is required")
    private String key;

    private String value;

    @Min(0) @Max(100)
    private double score;
}