package com.ninja.AnimalTypeClassification.dto;
import com.ninja.AnimalTypeClassification.entity.Age;
import com.ninja.AnimalTypeClassification.entity.enums.Gender;
import jakarta.validation.constraints.*;
        import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalDto {

    private Long id;

    @NotBlank(message = "Ear ID cannot be blank")
    @Size(max = 20, message = "Ear ID must not exceed 50 characters")
    private String earId;

    @NotBlank(message = "Species is required")
    @Size(max = 20, message = "Species must not exceed 100 characters")
    private String species;

    @NotBlank(message = "Breed is required")
    @Size(max = 20, message = "Breed must not exceed 100 characters")
    private String breed;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Age details are required")
    private Age age;

//    @Size(max = 5, message = "A maximum of 5 photo URLs are allowed")
//    private List<@NotBlank(message = "Photo URL cannot be blank") @Pattern(
//            regexp = "^(http|https)://.*$",
//            message = "Photo URL must be valid"
//    ) String> photoUrls;

    private List<String> photoUrls;

    private Long classificationRecordId;
}
