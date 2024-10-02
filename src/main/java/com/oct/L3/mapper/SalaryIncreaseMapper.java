package com.oct.L3.mapper;

import com.oct.L3.dtos.response.SalaryIncreaseResponse;
import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.entity.SalaryIncreaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalaryIncreaseMapper {

    private final EventFormMapper EventFormMapper;

    public SalaryIncreaseDTO toDTO(SalaryIncreaseEntity entity) {
        return SalaryIncreaseDTO.builder()
                .salaryIncreaseId(entity.getId())
                .times(entity.getTimes())
                .reason(entity.getReason())
                .level(entity.getLevel())
                .note(entity.getNote())
                .eventForm(entity.getEventFormId() == null ? null : EventFormMapper.toDTO(entity.getEventFormId()))
                .build();
    }

    public SalaryIncreaseEntity toEntity(SalaryIncreaseDTO dto) {
        return SalaryIncreaseEntity.builder()
                .Id(dto.getSalaryIncreaseId())
                .times(dto.getTimes())
                .reason(dto.getReason())
                .level(dto.getLevel())
                .note(dto.getNote())
                .eventFormId(dto.getEventForm() == null ? null : EventFormMapper.toEntity(dto.getEventForm()))
                .build();
    }
    public SalaryIncreaseResponse toResponse(SalaryIncreaseDTO dto) {
        return SalaryIncreaseResponse.builder()
                .salaryIncreaseId(dto.getSalaryIncreaseId())
                .times(dto.getTimes())
                .reason(dto.getReason())
                .level(dto.getLevel())
                .note(dto.getNote())
                .eventForm(dto.getEventForm())
                .build();
    }
}
