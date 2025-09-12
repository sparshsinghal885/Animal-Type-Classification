package com.ninja.AnimalTypeClassification.services;

import com.ninja.AnimalTypeClassification.entity.ClassificationDetail;

import java.util.List;

public interface ClassificationDetailService {
    public List<ClassificationDetail> getDetailsByRecordId(Long recordId);
}
