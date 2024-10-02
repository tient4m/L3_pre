package com.oct.L3.dtos;

import com.oct.L3.dtos.eventform.EventFormDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaryIncreaseDTO {
    private Integer salaryIncreaseId;
    private EventFormDTO eventForm;
    private Integer times;
    private String reason;
    private String level;
    private String note;
}