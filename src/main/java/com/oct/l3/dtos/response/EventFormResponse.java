package com.oct.l3.dtos.response;

import com.oct.l3.dtos.request.EmployeeRequest;
import com.oct.l3.dtos.EventFormHistoryDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventFormResponse{
    private Integer eventFormId;
    private String type;
    private Date date;
    private Date submissionDate;
    private String content;
    private String status;
    private String note;
    private EmployeeRequest employee;
    private List<EventFormHistoryDTO> histories;
}
