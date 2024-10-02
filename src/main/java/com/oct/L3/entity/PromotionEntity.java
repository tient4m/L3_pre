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
public class PromotionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Integer eventFormId;
    private Integer times;
    private String reason;
    private Integer oldPositionId;
    private Integer newPositionId;
    private String note;

}
