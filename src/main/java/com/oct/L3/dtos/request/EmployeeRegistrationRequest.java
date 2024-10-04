package com.oct.L3.dtos.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegistrationRequest {
    private String content;
    private String note;
    private String managerComments;
    private Integer employeeId;
    private Integer positionId;
    private Integer leaderId;
}
