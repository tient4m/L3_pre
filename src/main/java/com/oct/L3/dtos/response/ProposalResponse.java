package com.oct.L3.dtos.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProposalResponse {
    private Integer proposalId;
    private String content;
    private String type;
    private String description;
    private String note;
    private EventFormResponse eventForm;
}
