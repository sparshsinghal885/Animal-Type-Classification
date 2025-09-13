package com.ninja.AnimalTypeClassification.controller;

import com.ninja.AnimalTypeClassification.dto.AnimalDto;
import com.ninja.AnimalTypeClassification.entity.Animal;
import com.ninja.AnimalTypeClassification.services.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping("/{userId}")
    public ResponseEntity<Animal> addAnimalToUser(@Valid @RequestBody AnimalDto animalDto, @PathVariable Long userId){
        Animal res = animalService.addAnimalToUser(animalDto, userId);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/{earId}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable String earId,@RequestBody AnimalDto animalDto){
        Animal animal = animalService.updateAnimal(earId, animalDto);

        return new ResponseEntity<>(animal, HttpStatus.OK);
    }

    @DeleteMapping("/{earId}")
    public ResponseEntity<?> deleteAnimal(@PathVariable String earId){
        try {
            animalService.deleteAnimal(earId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Animal with this earId can't be deleted");
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/{earId}")
    public ResponseEntity<Animal> getAnimalByEarId(@PathVariable String earId){
        Animal animal = animalService.getAnimalByEarId(earId);

        if(animal == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(animal, HttpStatus.FOUND);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Animal>> getUserAnimals(@PathVariable Long userId){
        List<Animal> animals = animalService.getAllAnimals(userId);

        return new ResponseEntity<>(animals, HttpStatus.OK);
    }

}
