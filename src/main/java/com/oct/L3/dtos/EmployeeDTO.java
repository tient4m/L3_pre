package com.oct.L3.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oct.L3.entity.Certificate;
import com.oct.L3.entity.FamilyRelationship;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Integer id;
    private String name;
    private String code;
    private String gender;
    private Date dateOfBirth;
    private String address;
    private String identityCard;
    private String phoneNumber;

    @Email
    private String email;
    private Integer positionId;
    private Integer managerId;
    private String status;
    private String hometown;
    private String ethnicity;
    private String educationLevel;
    private List<CertificateDTO> certificates;
    private List<FamilyRelationshipDTO> familyRelationships;
}
