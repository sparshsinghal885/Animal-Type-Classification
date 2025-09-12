package com.ninja.AnimalTypeClassification.services.impl;

import com.ninja.AnimalTypeClassification.entity.ClassificationDetail;
import com.ninja.AnimalTypeClassification.repository.ClassificationDetailRepository;
import com.ninja.AnimalTypeClassification.repository.ClassificationRecordRepository;
import com.ninja.AnimalTypeClassification.services.ClassificationDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassificationDetailServiceImpl implements ClassificationDetailService {

    private final ClassificationDetailRepository detailRepository;
    private final ClassificationRecordRepository recordRepository;
    @Override
    public List<ClassificationDetail> getDetailsByRecordId(Long recordId) {

        if(detailRepository.existsById(recordId) == false){
            throw new RuntimeException("No record exists with this id :" + recordId);
        }

        return detailRepository.findByClassificationRecordId(recordId);
    }
}
