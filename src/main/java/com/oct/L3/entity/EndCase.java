package com.oct.L3.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "endcase")
public class EndCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_case_id")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "event_form_id")
    private EventForm eventForm;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "decision_number")
    private String decisionNumber;

    @Column(name = "file_number")
    private String fileNumber;

    @Column(name = "status")
    private String status;

}
