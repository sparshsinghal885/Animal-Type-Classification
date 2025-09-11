package com.ninja.AnimalTypeClassification.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassificationRecordDto {
    private Long id;
    private double finalScore;
    private List<ClassificationDetailDto> classificationDetails;
}
