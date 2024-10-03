package com.oct.L3.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProposalDTO {
    private Integer proposalId;
    private EventFormDTO eventFormDTO;
    private String content;
    private String type;
    private String description;
    private String note;
}
