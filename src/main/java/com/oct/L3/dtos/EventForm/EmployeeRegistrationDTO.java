package com.oct.L3.dtos.EventForm;

import com.oct.L3.dtos.CertificateDTO;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.dtos.FamilyRelationshipDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class EmployeeRegistrationDTO {
    private EmployeeDTO employee;
    private Date date;
    private Date submissionDate;
    private String content;
    private Integer leaderId;
    private String status;
    private String note;
}

