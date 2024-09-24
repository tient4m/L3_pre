package com.oct.L3.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FamilyRelationshipDTO {
    private Integer id;
    private String fullName;
    private String gender;
    private Date dateOfBirth;
    private String identityCard;
    private String relationship;
    private String address;
    private String phoneNumber;
    private String email;
    private String job;
}

