package com.oct.L3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
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
