package com.oct.L3.mapper;

import com.oct.L3.dtos.response.SalaryIncreaseResponse;
import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.entity.SalaryIncreaseEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalaryIncreaseMapper {

  private final ModelMapper modelMapper;

    public SalaryIncreaseDTO toDTO(SalaryIncreaseEntity salaryIncreaseEntity) {
        return modelMapper.map(salaryIncreaseEntity, SalaryIncreaseDTO.class);
    }

    public SalaryIncreaseEntity toEntity(SalaryIncreaseDTO salaryIncreaseDTO) {
        return modelMapper.map(salaryIncreaseDTO, SalaryIncreaseEntity.class);
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
