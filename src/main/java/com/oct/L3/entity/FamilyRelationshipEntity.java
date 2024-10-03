package com.oct.L3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "familyrelationship")
public class FamilyRelationshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private Integer id;
    private Integer employeeId;
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
