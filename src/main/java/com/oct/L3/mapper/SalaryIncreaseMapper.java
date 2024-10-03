package com.oct.L3.mapper;

import com.oct.L3.dtos.eventform.EventFormDTO;
import com.oct.L3.dtos.response.SalaryIncreaseResponse;
import com.oct.L3.dtos.SalaryIncreaseDTO;
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
        return SalaryIncreaseDTO.builder()
                .salaryIncreaseId(salaryIncreaseEntity.getId())
                .times(salaryIncreaseEntity.getTimes())
                .reason(salaryIncreaseEntity.getReason())
                .level(salaryIncreaseEntity.getLevel())
                .note(salaryIncreaseEntity.getNote())
                .eventFormId(salaryIncreaseEntity.getEventFormId())
                .build();
    }

    public SalaryIncreaseEntity toEntity(SalaryIncreaseDTO salaryIncreaseDTO) {
        return SalaryIncreaseEntity.builder()
                .id(salaryIncreaseDTO.getSalaryIncreaseId())
                .times(salaryIncreaseDTO.getTimes())
                .reason(salaryIncreaseDTO.getReason())
                .level(salaryIncreaseDTO.getLevel())
                .note(salaryIncreaseDTO.getNote())
                .eventFormId(salaryIncreaseDTO.getEventFormId())
                .build();
    }


    public SalaryIncreaseResponse toResponse(SalaryIncreaseDTO dto) {

        EventFormDTO eventFormDTO = eventFormMapper.toDTO(eventFormRepository.findById(dto.getEventFormId()).get());

        return SalaryIncreaseResponse.builder()
                .salaryIncreaseId(dto.getSalaryIncreaseId())
                .times(dto.getTimes())
                .reason(dto.getReason())
                .level(dto.getLevel())
                .note(dto.getNote())
                .eventForm(eventFormDTO)
                .build();
    }
}
