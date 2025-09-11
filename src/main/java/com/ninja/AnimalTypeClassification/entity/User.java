package com.ninja.AnimalTypeClassification.entity;

import com.ninja.AnimalTypeClassification.entity.enums.Category;
import com.ninja.AnimalTypeClassification.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String AdhaarNo;

    private String MobileNo;

    private String fullName;

    private String fatherName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Animal> animals = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
