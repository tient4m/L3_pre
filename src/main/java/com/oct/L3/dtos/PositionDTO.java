package com.oct.L3.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionDTO {
    private Integer positionId;
    private String name;
    private String description;
}
