package com.oct.L3.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTO {
    private Integer id;
    private EventFormDTO eventFormDTO;
    private Integer times;
    private String reason;
    private Integer oldPositionId;
    private Integer newPositionId;
    private String note;
}