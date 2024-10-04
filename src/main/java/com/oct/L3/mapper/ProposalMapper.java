package com.oct.L3.mapper;

import com.oct.L3.dtos.EventFormDTO;
import com.oct.L3.dtos.response.ProposalResponse;
import com.oct.L3.dtos.ProposalDTO;
import com.oct.L3.entity.EventFormEntity;
import com.oct.L3.entity.ProposalEntity;
import com.oct.L3.repository.EventFormRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProposalMapper {

    private final EventFormMapper eventFormMapper;
    private final EventFormRepository eventFormRepository;

    public ProposalDTO toDTO(ProposalEntity proposalEntity) {

        EventFormEntity eventFormEntity = eventFormRepository.findById(proposalEntity.getEventFormId())
                .orElseThrow(() -> new RuntimeException("EventFormEntity not found"));

        return ProposalDTO.builder()
                .proposalId(proposalEntity.getId())
                .content(proposalEntity.getContent())
                .type(proposalEntity.getType())
                .description(proposalEntity.getDescription())
                .note(proposalEntity.getNote())
                .eventFormDTO(eventFormMapper.toDTO(eventFormEntity))
                .build();
    }

    public ProposalEntity toEntity(ProposalDTO proposalDTO) {
        return ProposalEntity.builder()
                .id(proposalDTO.getProposalId())
                .content(proposalDTO.getContent())
                .type(proposalDTO.getType())
                .description(proposalDTO.getDescription())
                .note(proposalDTO.getNote())
                .eventFormId(proposalDTO.getEventFormDTO().getId())
                .build();
    }

    public ProposalResponse toResponse(ProposalDTO dto) {

        return ProposalResponse.builder()
                .proposalId(dto.getProposalId())
                .content(dto.getContent())
                .type(dto.getType())
                .description(dto.getDescription())
                .note(dto.getNote())
                .eventForm(dto.getEventFormDTO())
                .build();
    }

}
