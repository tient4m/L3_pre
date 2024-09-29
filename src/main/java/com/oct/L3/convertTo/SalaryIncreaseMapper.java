package com.oct.L3.convertTo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oct.L3.Response.SalaryIncreaseResponse;
import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.entity.SalaryIncrease;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalaryIncreaseMapper {

    private final EventFormMapper EventFormMapper;

    public SalaryIncreaseDTO toDTO(SalaryIncrease entity) {
        return SalaryIncreaseDTO.builder()
                .salaryIncreaseId(entity.getId())
                .times(entity.getTimes())
                .reason(entity.getReason())
                .level(entity.getLevel())
                .note(entity.getNote())
                .eventForm(entity.getEventForm() == null ? null : EventFormMapper.toDTO(entity.getEventForm()))
                .build();
    }

    public SalaryIncrease toEntity(SalaryIncreaseDTO dto) {
        return SalaryIncrease.builder()
                .Id(dto.getSalaryIncreaseId())
                .times(dto.getTimes())
                .reason(dto.getReason())
                .level(dto.getLevel())
                .note(dto.getNote())
                .eventForm(dto.getEventForm() == null ? null : EventFormMapper.toEntity(dto.getEventForm()))
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
