package com.ninja.AnimalTypeClassification.services;

import com.ninja.AnimalTypeClassification.dto.UserDto;
import com.ninja.AnimalTypeClassification.entity.User;

import java.util.List;

public interface UserService {

    public User createUser(UserDto userdto);

    public User findUserById(Long userid) throws Exception;

    public List<User> findAllUsers();

    public User updateUser(Long userId, UserDto userDto);

    public void deleteUser(Long userId);
}
