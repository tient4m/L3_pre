package com.oct.L3.dtos.EventForm;

import com.oct.L3.dtos.CertificateDTO;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.dtos.FamilyRelationshipDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegistrationDTO {
    private EmployeeDTO employeeData;
    private String type;
    private Date  date;
    private String content;
    private Integer managerId;
    private String status;
    private String note;
}

