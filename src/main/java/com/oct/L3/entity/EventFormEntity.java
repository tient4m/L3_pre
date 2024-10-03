package com.oct.L3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_form")
public class EventFormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer employeeId;
    private String managerComments;
    private String leaderComments;
    private String type;
    private Date  date;
    private Date submissionDate;
    private String content;
    private String status;
    private Integer leaderId;
    private Integer managerId;
    private String note;

}
