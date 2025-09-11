package com.ninja.AnimalTypeClassification.dto;

import com.ninja.AnimalTypeClassification.entity.enums.Category;
import com.ninja.AnimalTypeClassification.entity.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank
    @Pattern(regexp = "\\d{12}", message = "Adhaar number must be 12 digits")
    private String adhaarNo;

    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobileNo;

    @NotBlank
    private String fullName;

    @NotBlank
    private String fatherName;

    @NotNull
    private Gender gender;


    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Category category;
}
