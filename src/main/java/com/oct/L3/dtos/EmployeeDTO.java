package com.oct.L3.dtos;

import com.oct.L3.validator.ageconstraint.AgeConstraint;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Integer id;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must not contain numbers or special characters")
    private String name;

    @NotBlank(message = "EmployeeEntity code is required")
    @Pattern(regexp = "^[A-Za-z0-9]{5,10}$", message = "Code must be 5-10 characters long and contain only letters and numbers")
    private String code;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be either Male, Female, or Other")
    private String gender;

    @Past(message = "Date of birth must be in the past")
    @AgeConstraint()
    private Date dateOfBirth;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Identity card is required")
    @Pattern(regexp = "^[0-9]+$", message = "Identity card must only contain numbers")
    @Size(min = 9, max = 15, message = "Identity card must be between 9 and 15 digits")
    private String identityCard;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Phone number must only contain 10 or 11 digits")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    private Integer positionId;
    private Integer managerId;
    @NotBlank(message = "Status is required")
    private String status;

    private String hometown;
    private String ethnicity;
    private String educationLevel;
    private List<CertificateDTO> certificates;
    private List<FamilyRelationshipDTO> familyRelationships;
}
