package com.ninja.AnimalTypeClassification.services;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;

import java.util.List;

public interface AIService {
    public List<ClassificationDetailDto> classifyImage(String imageUrl);
}
