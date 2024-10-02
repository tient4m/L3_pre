package com.oct.L3.mapper;

import com.oct.L3.dtos.response.ProposalResponse;
import com.oct.L3.dtos.ProposalDTO;
import com.oct.L3.entity.ProposalEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProposalMapper {
    private final EventFormMapper eventFormMapper;

    public ProposalDTO toDTO(ProposalEntity entity) {
        return ProposalDTO.builder()
                .proposalId(entity.getId())
                .eventForm(entity.getEventFormId() == null ? null : eventFormMapper.toDTO(entity.getEventFormId()))
                .content(entity.getContent())
                .type(entity.getType())
                .description(entity.getDescription())
                .note(entity.getNote())
                .build();
    }

    public ProposalEntity toEntity(ProposalDTO dto) {
        return ProposalEntity.builder()
                .Id(dto.getProposalId())
                .eventFormId(dto.getEventForm() == null ? null : eventFormMapper.toEntity(dto.getEventForm()))
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
