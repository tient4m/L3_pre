package com.oct.L3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "endcase")
public class EndCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_case_id")
    private Integer Id;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "decision_date")
    private Date decisionDate;

    @Column(name = "archive_number")
    private String archiveNumber;

    @Column(name = "status")
    private String status;

}
