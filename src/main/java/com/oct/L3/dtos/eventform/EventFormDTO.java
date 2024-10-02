package com.oct.L3.dtos.eventform;

import com.oct.L3.dtos.EventFormHistoryDTO;
import lombok.*;

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