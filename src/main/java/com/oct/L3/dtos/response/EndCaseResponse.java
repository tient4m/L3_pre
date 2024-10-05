package com.oct.L3.dtos.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndCaseResponse {
    private Integer Id;
    private Date endDate;
    private String archiveNumber;
    private String reason;
    private EventFormResponse eventForm;
}
