package com.ninja.AnimalTypeClassification.repository;

import com.ninja.AnimalTypeClassification.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
