package com.oct.L3.dtos;

import com.oct.L3.dtos.eventform.EventFormDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProposalDTO {
    private Integer proposalId;
    private EventFormDTO eventForm;
    private String content;
    private String type;
    private String description;
    private String note;
}
