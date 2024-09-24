package com.oct.L3.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventFormHistoryDTO {
    private Integer id;

    private Integer eventFormId;

    private String status;

    private Date date;

    private String note;
}
