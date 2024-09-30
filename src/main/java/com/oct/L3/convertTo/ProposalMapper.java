package com.oct.L3.convertTo;

import com.oct.L3.Response.ProposalResponse;
import com.oct.L3.dtos.ProposalDTO;
import com.oct.L3.entity.Proposal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProposalMapper {
    private final EventFormMapper eventFormMapper;

    public ProposalDTO toDTO(Proposal entity) {
        return ProposalDTO.builder()
                .proposalId(entity.getId())
                .eventForm(entity.getEventForm() == null ? null : eventFormMapper.toDTO(entity.getEventForm()))
                .content(entity.getContent())
                .type(entity.getType())
                .description(entity.getDescription())
                .note(entity.getNote())
                .build();
    }

    public Proposal toEntity(ProposalDTO dto) {
        return Proposal.builder()
                .Id(dto.getProposalId())
                .eventForm(dto.getEventForm() == null ? null : eventFormMapper.toEntity(dto.getEventForm()))
                .content(dto.getContent())
                .type(dto.getType())
                .description(dto.getDescription())
                .note(dto.getNote())
                .build();
    }

    public ProposalResponse toResponse(ProposalDTO dto) {
        ProposalResponse proposalResponse = ProposalResponse.builder()
                .proposalId(dto.getProposalId())
                .content(dto.getContent())
                .type(dto.getType())
                .description(dto.getDescription())
                .note(dto.getNote())
                .eventForm(dto.getEventForm())
                .build();
        return proposalResponse;
    }

}
