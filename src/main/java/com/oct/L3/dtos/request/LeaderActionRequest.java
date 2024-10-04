package com.oct.L3.dtos.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaderActionRequest {
    private Integer eventFormId;
    private String leaderComments;
}
