package com.oct.L3.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaryIncreaseDTO {
    private Integer salaryIncreaseId;
    private EventFormDTO eventFormDTO;
    private Integer times;
    private String reason;
    private String level;
    private String note;
}