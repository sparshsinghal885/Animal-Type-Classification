package com.ninja.AnimalTypeClassification.services.impl;

import com.ninja.AnimalTypeClassification.repository.AnimalRepository;
import com.ninja.AnimalTypeClassification.repository.UserRepository;
import com.ninja.AnimalTypeClassification.response.DashBoardStatsResponse;
import com.ninja.AnimalTypeClassification.services.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DashBoardServiceImpl implements DashBoardService{

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;

    public DashBoardStatsResponse getStats() {
        long users = userRepository.count();
        long animals = animalRepository.count();
        return new DashBoardStatsResponse(users, animals);
    }
}
