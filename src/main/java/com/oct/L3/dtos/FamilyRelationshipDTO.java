package com.oct.L3.dtos;

import com.oct.L3.validator.AgeConstraint;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FamilyRelationshipDTO {

    private Integer id;

    @NotBlank(message = "Full name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name must not contain numbers or special characters")
    private String fullName;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be either Male, Female, or Other")
    private String gender;

    @Past(message = "Date of birth must be in the past")
    @AgeConstraint(min = 0, max = 100, message = "Age must be between 0 and 100 years")
    private Date dateOfBirth;

    @NotBlank(message = "Identity card is required")
    @Pattern(regexp = "^[0-9]+$", message = "Identity card must only contain numbers")
    @Size(min = 9, max = 12, message = "Identity card must be between 9 and 12 digits")
    private String identityCard;

    @NotBlank(message = "Relationship is required")
    private String relationship;

    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Phone number must only contain 10 or 11 digits")
    private String phoneNumber;

    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Job is required")
    private String job;

}

