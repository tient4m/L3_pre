package com.oct.L3.dtos.response;

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
    private EventFormResponse eventForm;
}
