package com.oct.L3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "salaryincrease")
public class SalaryIncrease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_increase_id")
    private Integer Id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "event_form_id")
    private EventForm eventForm;

    @Column(name = "times")
    private Integer times;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "level")
    private String level;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;


}
