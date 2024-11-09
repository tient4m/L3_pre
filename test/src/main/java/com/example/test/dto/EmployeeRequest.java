package com.example.test.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private Integer id;
    private String name;

    private String code;

    private String gender;

    private Date dateOfBirth;
    private String address;

    private String identityCard;

    private String phoneNumber;

    private String email;

    private Integer positionId;
    private Integer managerId;
    private String status;

    private String hometown;
    private String ethnicity;
    private String educationLevel;
}
