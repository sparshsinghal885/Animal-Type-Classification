package com.ninja.AnimalTypeClassification.services;

import com.ninja.AnimalTypeClassification.dto.AnimalDto;
import com.ninja.AnimalTypeClassification.entity.Animal;

import java.util.List;

public interface AnimalService {
    public Animal addAnimalToUser(AnimalDto animalDto, Long userId);

    public List<Animal> getAllAnimals(Long userId);

    public Animal getAnimalByEarId(String earId);

    public Animal updateAnimal(String earId, AnimalDto animalDto);

    public void deleteAnimal(String earId);

}