package com.oct.L3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer Id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "role")
    private String role;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    public User(Integer leaderId) {
    }
}
