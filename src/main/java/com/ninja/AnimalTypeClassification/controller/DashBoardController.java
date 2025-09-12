package com.ninja.AnimalTypeClassification.controller;

import com.ninja.AnimalTypeClassification.response.DashBoardStatsResponse;
import com.ninja.AnimalTypeClassification.services.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dashboard")
public class DashBoardController {
    private final DashBoardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashBoardStatsResponse> getStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }
}
