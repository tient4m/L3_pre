package com.oct.L3.mapper;

import com.oct.L3.dtos.response.SalaryIncreaseResponse;
import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.entity.EventFormEntity;
import com.oct.L3.entity.SalaryIncreaseEntity;
import com.oct.L3.repository.EventFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalaryIncreaseMapper {

    private final EventFormMapper eventFormMapper;
    private final EventFormRepository eventFormRepository;

    public SalaryIncreaseDTO toDTO(SalaryIncreaseEntity salaryIncreaseEntity) {
        EventFormEntity eventFormEntity = eventFormRepository.findById(salaryIncreaseEntity.getEventFormId())
                .orElseThrow(() -> new RuntimeException("EventFormEntity not found"));

        return SalaryIncreaseDTO.builder()
                .id(salaryIncreaseEntity.getId())
                .times(salaryIncreaseEntity.getTimes())
                .reason(salaryIncreaseEntity.getReason())
                .level(salaryIncreaseEntity.getLevel())
                .note(salaryIncreaseEntity.getNote())
                .eventFormDTO(eventFormMapper.toDTO(eventFormEntity))
                .build();
    }

    public SalaryIncreaseEntity toEntity(SalaryIncreaseDTO salaryIncreaseDTO) {
        return SalaryIncreaseEntity.builder()
                .id(salaryIncreaseDTO.getId())
                .times(salaryIncreaseDTO.getTimes())
                .reason(salaryIncreaseDTO.getReason())
                .level(salaryIncreaseDTO.getLevel())
                .note(salaryIncreaseDTO.getNote())
                .eventFormId(salaryIncreaseDTO.getEventFormDTO().getId())
                .build();
    }


    public SalaryIncreaseResponse toResponse(SalaryIncreaseDTO dto) {

        return SalaryIncreaseResponse.builder()
                .salaryIncreaseId(dto.getId())
                .times(dto.getTimes())
                .reason(dto.getReason())
                .level(dto.getLevel())
                .note(dto.getNote())
                .eventForm(eventFormMapper.toResponse(dto.getEventFormDTO()))
                .build();
    }

    public SalaryIncreaseResponse toResponse(SalaryIncreaseEntity entity) {
        return toResponse(toDTO(entity));
    }
}
