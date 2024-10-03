package com.oct.L3.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndCaseDTO {
    private Integer Id;
    private EventFormDTO eventFormDTO;
    private Integer employeeId;
    private Date endDate;
    private String archiveNumber;
    private String reason;
}