package com.ninja.AnimalTypeClassification.controller;

import com.ninja.AnimalTypeClassification.entity.ClassificationDetail;
import com.ninja.AnimalTypeClassification.services.ClassificationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/records/{recordId}/details")
public class ClassificationDetailController {

    @Autowired
    private ClassificationDetailService detailService;

    @GetMapping
    public ResponseEntity<List<ClassificationDetail>> getDetailsByRecordId(@PathVariable Long recordId) {
        List<ClassificationDetail> details = detailService.getDetailsByRecordId(recordId);
        return ResponseEntity.ok(details);
    }
}
