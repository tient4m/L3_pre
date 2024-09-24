package com.oct.L3.Response;

import com.oct.L3.entity.Position;
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
    private Position oldPosition;
    private Position newPosition;
    private String note;
}
