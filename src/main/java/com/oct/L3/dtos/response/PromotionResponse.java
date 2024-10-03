package com.oct.L3.dtos.response;

import com.oct.L3.dtos.PositionDTO;
import com.oct.L3.dtos.EventFormDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromotionResponse {
    private Integer promotionId;
    private Integer times;
    private String reason;
    private PositionDTO oldPosition;
    private PositionDTO newPosition;
    private String note;
    private EventFormDTO eventForm;
}
