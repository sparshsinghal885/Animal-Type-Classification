package com.ninja.AnimalTypeClassification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClassificationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> photoUrls = new ArrayList<>();

    @JsonIgnore
    @ManyToOne // owning side (mapping domain)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private double finalScore;

    @JsonIgnore
    @OneToMany(mappedBy = "classificationRecord", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ClassificationDetail> classificationDetail;
}
