package com.ninja.AnimalTypeClassification.repository;

import com.ninja.AnimalTypeClassification.entity.ClassificationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ClassificationRecordRepository extends JpaRepository<ClassificationRecord, Long> {

    List<ClassificationRecord> findByAnimalId(Long animalId);
}