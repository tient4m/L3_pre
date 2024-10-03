package com.oct.L3.dtos;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventFormDTO {
    private Integer id;
    private Integer employeeId;
    private String managerComments;
    private String leaderComments;
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