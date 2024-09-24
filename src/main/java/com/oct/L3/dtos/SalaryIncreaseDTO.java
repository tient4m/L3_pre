package com.oct.L3.dtos;

import com.oct.L3.dtos.EventForm.EventFormDTO;
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