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
public class SalaryIncreaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Integer eventFormId;
    private Integer times;
    private String reason;
    private String level;
    private String note;


}
