package com.oct.L3.mapper;

import com.oct.L3.dtos.EndCaseDTO;
import com.oct.L3.dtos.EventFormDTO;
import com.oct.L3.dtos.response.EndCaseResponse;
import com.oct.L3.entity.EndCaseEntity;
import com.oct.L3.repository.EventFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EndCaseMapper {
    private final EventFormRepository eventFormRepository;
    private final EventFormMapper eventFormMapper;

    public EndCaseDTO toDTO(EndCaseEntity endCaseEntity) {

        EventFormDTO eventFormDTO = eventFormRepository.findById(endCaseEntity.getEventFormId())
                .map(eventFormMapper::toDTO)
                .orElse(null);

        return EndCaseDTO.builder()
                .Id(endCaseEntity.getId())
                .eventFormDTO(eventFormDTO)
                .endDate(endCaseEntity.getEndDate())
                .archiveNumber(endCaseEntity.getArchiveNumber())
                .reason(endCaseEntity.getReason())
                .build();
    }

    public EndCaseEntity toEntity(EndCaseDTO endCaseDTO) {
        return EndCaseEntity.builder()
                .id(endCaseDTO.getId())
                .eventFormId(endCaseDTO.getEventFormDTO().getId())
                .endDate(endCaseDTO.getEndDate())
                .archiveNumber(endCaseDTO.getArchiveNumber())
                .reason(endCaseDTO.getReason())
                .build();
    }

    public EndCaseResponse toResponse(EndCaseDTO dto) {
        return EndCaseResponse.builder()
                .Id(dto.getId())
                .endDate(dto.getEndDate())
                .archiveNumber(dto.getArchiveNumber())
                .reason(dto.getReason())
                .eventForm(eventFormMapper.toResponse(dto.getEventFormDTO()))
                .build();
    }

    public EndCaseResponse toResponse(EndCaseEntity entity) {
        return toResponse(toDTO(entity));
    }
}
