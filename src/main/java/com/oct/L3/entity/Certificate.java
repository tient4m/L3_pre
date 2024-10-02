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
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "name")
    private String name;

    @Column(name = "field")
    private String field;

    @Column(name = "issue_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;

    @Column(name = "description")
    private String description;

}