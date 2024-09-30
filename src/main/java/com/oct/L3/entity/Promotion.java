package com.oct.L3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "promotion")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Integer Id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "event_form_id")
    private EventForm eventForm;

    @Column(name = "times")
    private Integer times;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "old_position_id")
    private Position oldPosition;

    @ManyToOne
    @JoinColumn(name = "new_position_id")
    private Position newPosition;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

}
