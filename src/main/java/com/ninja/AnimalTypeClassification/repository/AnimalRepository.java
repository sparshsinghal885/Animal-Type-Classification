package com.ninja.AnimalTypeClassification.repository;

import com.ninja.AnimalTypeClassification.entity.Animal;
import com.ninja.AnimalTypeClassification.entity.ClassificationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    public Animal findByEarId(String earId);
    public long count();
}