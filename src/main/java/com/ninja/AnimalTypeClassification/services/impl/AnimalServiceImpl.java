package com.ninja.AnimalTypeClassification.services.impl;

import com.ninja.AnimalTypeClassification.dto.AnimalDto;
import com.ninja.AnimalTypeClassification.entity.Animal;
import com.ninja.AnimalTypeClassification.entity.User;
import com.ninja.AnimalTypeClassification.repository.AnimalRepository;
import com.ninja.AnimalTypeClassification.repository.UserRepository;
import com.ninja.AnimalTypeClassification.services.AnimalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;

    @Override
    @Transactional
    public Animal addAnimalToUser(AnimalDto animalDto, Long userId) {

        User user = userRepository.findById(userId).orElse(null);

        Animal animal = Animal.builder()
                .earId(animalDto.getEarId())
                .species(animalDto.getSpecies())
                .breed(animalDto.getBreed())
                .age(animalDto.getAge())
                .gender(animalDto.getGender())
                .user(user)
                .photoUrls(animalDto.getPhotoUrls())
                .build();

        Animal savedAnimal = animalRepository.save(animal);

        user.getAnimals().add(savedAnimal);

        userRepository.save(user);

        return savedAnimal;
    }

    @Override
    public List<Animal> getAllAnimals(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return user.getAnimals();
    }

    @Override
    public Animal getAnimalByEarId(String earId) {
        Animal animal = animalRepository.findByEarId(earId);

        return animal;
    }

    @Transactional
    @Override
    public Animal updateAnimal(String earId, AnimalDto animalDto) {
        Animal animal = animalRepository.findByEarId(earId);
        if(animal == null){
            throw  new RuntimeException("Animal does not exists with this id :" + earId);
        }
        animal.setSpecies(animal.getSpecies());
        animal.setBreed(animal.getBreed());
        animal.setAge(animalDto.getAge());
        animal.setGender(animalDto.getGender());

        return animal;
    }

    @Transactional
    @Override
    public void deleteAnimal(String earId) {
        Animal animal = animalRepository.findByEarId(earId);
        if(animal == null){
            throw  new RuntimeException("Animal does not exists with this id :" + earId);
        }
        animalRepository.delete(animal);
    }
}
