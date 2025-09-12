package com.ninja.AnimalTypeClassification.repository;

import com.ninja.AnimalTypeClassification.entity.ClassificationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ClassificationDetailRepository extends JpaRepository<ClassificationDetail, Long> {
    List<ClassificationDetail> findByClassificationRecordId(Long recordId);
}