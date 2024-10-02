package com.oct.L3.dtos.response;

import com.oct.L3.dtos.eventform.EventFormDTO;
import com.oct.L3.entity.PositionEntity;
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
    private PositionEntity oldPositionEntity;
    private PositionEntity newPositionEntity;
    private String note;
    private EventFormDTO eventForm;
}
