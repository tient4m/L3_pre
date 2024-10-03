package com.oct.L3.mapper;

import com.oct.L3.dtos.EventFormDTO;
import com.oct.L3.dtos.response.ProposalResponse;
import com.oct.L3.dtos.ProposalDTO;
import com.oct.L3.entity.ProposalEntity;
import com.oct.L3.repository.EventFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProposalMapper {

    private final EventFormMapper eventFormMapper;
    private final EventFormRepository eventFormRepository;

    public ProposalDTO toDTO(ProposalEntity proposalEntity) {
        EventFormDTO eventFormDTO = eventFormMapper.toDTO(eventFormRepository.findById(proposalEntity.getEventFormId()).get());

        return ProposalDTO.builder()
                .proposalId(proposalEntity.getId())
                .content(proposalEntity.getContent())
                .type(proposalEntity.getType())
                .description(proposalEntity.getDescription())
                .note(proposalEntity.getNote())
                .eventFormDTO(eventFormDTO)
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
