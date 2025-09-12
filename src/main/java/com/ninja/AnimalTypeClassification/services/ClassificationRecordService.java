package com.ninja.AnimalTypeClassification.services;

import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.entity.ClassificationRecord;

import java.util.List;

public interface ClassificationRecordService {

    public ClassificationRecord createRecord(Long animalId, List<ClassificationDetailDto> detailsFromAI, List<String> imageUrls);

    ClassificationRecord getById(Long id);

    List<ClassificationRecord> getByAnimalId(Long animalId);

    void deleteById(Long id);

}
