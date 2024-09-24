package com.oct.L3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "eventform")
public class EventForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_form_id")
    private Integer Id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "employee", columnDefinition = "TEXT")
    private String employeeDataJson;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "date")
    private Date  date;

    @Column(name = "submission_date")
    private Date submissionDate;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private User leader;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @OneToMany(mappedBy = "eventForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventFormHistory> histories;
}
