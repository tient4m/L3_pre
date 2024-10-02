package com.oct.L3.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "certificateEntities")
public class CertificateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Integer employeeId;
    private String name;
    private String field;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;
    private String description;

}