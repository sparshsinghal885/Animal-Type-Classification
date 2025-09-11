package com.ninja.AnimalTypeClassification.services.impl;

import com.ninja.AnimalTypeClassification.dto.UserDto;
import com.ninja.AnimalTypeClassification.entity.User;
import com.ninja.AnimalTypeClassification.repository.AnimalRepository;
import com.ninja.AnimalTypeClassification.repository.UserRepository;
import com.ninja.AnimalTypeClassification.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;

    @Override
    @Transactional
    public User createUser(UserDto userdto) {
        User user = User.builder()
                .AdhaarNo(userdto.getAdhaarNo())
                .MobileNo(userdto.getMobileNo())
                .fullName(userdto.getFullName())
                .fatherName(userdto.getFatherName())
                .gender(userdto.getGender())
                .dateOfBirth(userdto.getDateOfBirth())
                .category(userdto.getCategory()).build();

        return userRepository.save(user);
    }



    @Override
    public User findUserById(Long userid) throws Exception {
        Optional<User> user = userRepository.findById(userid);

        if(user.isPresent()){
            return user.get();
        }else{
            throw new Exception("User not found with the given id : " + userid);
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow();

        user.setAdhaarNo(userDto.getAdhaarNo());
        user.setMobileNo(userDto.getMobileNo());
        user.setFullName(userDto.getFullName());
        user.setFatherName(userDto.getFatherName());
        user.setGender(userDto.getGender());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setCategory(userDto.getCategory());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if(user == null){
            throw  new RuntimeException("User does not exists with this id : " + userId);
        }

        userRepository.delete(user);
    }
}
