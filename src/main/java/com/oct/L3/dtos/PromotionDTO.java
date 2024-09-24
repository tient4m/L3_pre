package com.oct.L3.dtos;

import com.oct.L3.dtos.EventForm.EventFormDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTO {
    private Integer promotionId;
    private EventFormDTO eventForm;
    private Integer times;
    private String reason;
    private Integer oldPosition;
    private Integer newPosition;
    private String note;
}