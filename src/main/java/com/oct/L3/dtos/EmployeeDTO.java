package com.oct.L3.dtos;

import com.oct.L3.validator.AgeConstraint;
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

    @NotBlank(message = "Employee code is required")
    @Pattern(regexp = "^(?!.*(.).*\\1)[A-Za-z0-9]+$", message = "Code must not have repeated characters")
    private String code;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be either Male, Female, or Other")
    private String gender;

    @Past(message = "Date of birth must be in the past")
    @AgeConstraint(min = 18, max = 100, message = "Age must be between 18 and 100 years")
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
