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
@Table(name = "event_form_history")
public class EventFormHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "event_form_id", nullable = false)
    private EventForm eventForm;

    @Column(name = "request_date", nullable = false)
    private Date actionDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

}
