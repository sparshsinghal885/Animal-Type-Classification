package com.ninja.AnimalTypeClassification.services.impl;

import com.ninja.AnimalTypeClassification.entity.ClassificationDetail;
import com.ninja.AnimalTypeClassification.repository.ClassificationDetailRepository;
import com.ninja.AnimalTypeClassification.services.ClassificationDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassificationDetailServiceImpl implements ClassificationDetailService {

    private final ClassificationDetailRepository detailRepository;
    @Override
    public List<ClassificationDetail> getDetailsByRecordId(Long recordId) {
        return detailRepository.findByClassificationRecordId(recordId);
    }
}
