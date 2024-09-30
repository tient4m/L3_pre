package com.oct.L3.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndCaseDTO {
    private Integer endCaseId;
    private Integer employeeId;
    private Date decisionDate;
    private String archiveNumber;
    private String status;
}