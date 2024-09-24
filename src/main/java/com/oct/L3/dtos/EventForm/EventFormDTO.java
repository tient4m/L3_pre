package com.oct.L3.dtos.EventForm;

import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.dtos.EventFormHistoryDTO;
import com.oct.L3.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventFormDTO {
    private Integer Id;
    private Integer employeeId;
    private EmployeeDTO employeeData;
    private String type;
    private Date  date;
    private Date submissionDate;
    private String content;
    private Integer leaderId;
    private Integer managerId;
    private String status;
    private String note;
    private List<EventFormHistoryDTO> histories;
}