package com.oct.L3.dtos.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendToLeaderDTO {
    private Integer leaderId;
    private Integer eventFormId;
    private Date submissionDate;
    private String managerComments;
}
