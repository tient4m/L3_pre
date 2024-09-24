package com.oct.L3.Response;

import com.oct.L3.dtos.EventForm.EventFormDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaryIncreaseResponse {
    private Integer salaryIncreaseId;
    private Integer times;
    private String reason;
    private String level;
    private String note;
}
