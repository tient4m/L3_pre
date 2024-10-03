package com.oct.L3.mapper;

import com.oct.L3.dtos.response.ProposalResponse;
import com.oct.L3.dtos.ProposalDTO;
import com.oct.L3.entity.ProposalEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProposalMapper {
    private final EventFormMapper eventFormMapper;
    private final ModelMapper modelMapper;

    public ProposalDTO toDTO(ProposalEntity proposalEntity) {
        return modelMapper.map(proposalEntity, ProposalDTO.class);
    }

    public ProposalEntity toEntity(ProposalDTO proposalDTO) {
        return modelMapper.map(proposalDTO, ProposalEntity.class);
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
