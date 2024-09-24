package com.oct.L3.dtos;

import com.oct.L3.dtos.EventForm.EventFormDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndCaseDTO {
    private Integer endCaseId;
    private Integer eventFormId;
    private String reason;
    private String decisionNumber;
    private String fileNumber;
    private String status;
}