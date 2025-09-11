package com.ninja.AnimalTypeClassification.controller;

import com.ninja.AnimalTypeClassification.dto.UserDto;
import com.ninja.AnimalTypeClassification.entity.User;
import com.ninja.AnimalTypeClassification.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto){
        User user = userService.createUser(userDto);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) throws Exception {
        User user = userService.findUserById(userId);

        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users= userService.findAllUsers();

        return new ResponseEntity<>(users, HttpStatus.FOUND);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId){
        User user = userService.updateUser(userId, userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        try{
            userService.deleteUser(userId);
        }
        catch (RuntimeException e){
            throw  new RuntimeException("User can't be deleted with this userId :" + userId);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
