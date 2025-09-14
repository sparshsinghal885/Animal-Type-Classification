package com.ninja.AnimalTypeClassification.services.impl;


import com.ninja.AnimalTypeClassification.dto.ClassificationDetailDto;
import com.ninja.AnimalTypeClassification.entity.Animal;
import com.ninja.AnimalTypeClassification.entity.ClassificationDetail;
import com.ninja.AnimalTypeClassification.entity.ClassificationRecord;
import com.ninja.AnimalTypeClassification.repository.AnimalRepository;
import com.ninja.AnimalTypeClassification.repository.ClassificationRecordRepository;
import com.ninja.AnimalTypeClassification.services.ClassificationRecordService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClassificationRecordServiceImpl implements ClassificationRecordService {


    private final AnimalRepository animalRepository;
    private final ClassificationRecordRepository recordRepository;

    @Transactional
    public ClassificationRecord createRecord(Long animalId, List<ClassificationDetailDto> detailsFromAI, String imageUrl) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal not found with id: " + animalId));

        for (ClassificationDetailDto detail : detailsFromAI) {
            if ("Breed".equalsIgnoreCase(detail.getKey())) {
                animal.setBreed(detail.getValue());    // set breed
            }
            if("Species".equalsIgnoreCase(detail.getKey())){
                animal.setSpecies(detail.getValue());
            }
        }

        ClassificationRecord record = new ClassificationRecord();
        record.setAnimal(animal);
        record.setPhotoUrl(imageUrl);

        // map details
        List<ClassificationDetail> details = detailsFromAI.stream().map(dto -> {
            ClassificationDetail d = new ClassificationDetail();
            d.setTraitName(dto.getKey());
            d.setTraitValue(dto.getValue());
            d.setScore(dto.getScore());
            d.setClassificationRecord(record);
            return d;
        }).toList();

        record.setClassificationDetail(details);

        // compute average
        double avg = details.stream()
                .mapToDouble(ClassificationDetail::getScore)
                .average()
                .orElse(0.0);

        record.setFinalScore(avg);

        return recordRepository.save(record);
    }

    public List<ClassificationRecord> getByAnimalId(Long animalId) {
        return recordRepository.findByAnimalId(animalId);
    }

    public ClassificationRecord getById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found: " + id));
    }

    public void deleteById(Long id) {
        recordRepository.deleteById(id);
    }


}