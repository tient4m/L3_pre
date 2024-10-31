package com.oct.l3.service.impl;

import com.oct.l3.dtos.EventFormDTO;
import com.oct.l3.dtos.SalaryIncreaseDTO;
import com.oct.l3.dtos.response.SalaryIncreaseResponse;
import com.oct.l3.exceptions.DataNotFoundException;
import com.oct.l3.exceptions.InvalidStatusException;
import com.oct.l3.mapper.SalaryIncreaseMapper;
import com.oct.l3.repository.SalaryIncreaseRepository;
import com.oct.l3.service.EventFormService;
import com.oct.l3.service.SalaryIncreaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oct.l3.constant.EventType.SALARY_INCREASE;

@Service
@RequiredArgsConstructor
public class SalaryIncreaseServiceImpl implements SalaryIncreaseService {

    private final SalaryIncreaseRepository salaryIncreaseRepository;
    private final EventFormService eventFormService;
    private final SalaryIncreaseMapper salaryIncreaseMapper;

    @Transactional
    @Override
    public SalaryIncreaseResponse createSalaryIncrease(SalaryIncreaseDTO dto) {
        if (dto.getEventFormDTO() == null) {
            throw new InvalidStatusException("EventFormEntity is required");
        }
        dto.getEventFormDTO().setType(SALARY_INCREASE);
        EventFormDTO eventFormDTO = eventFormService.createEventForm(dto.getEventFormDTO());
        dto.setEventFormDTO(eventFormDTO);
        SalaryIncreaseDTO salaryIncreaseDTO = salaryIncreaseMapper.toDTO(salaryIncreaseRepository.save(salaryIncreaseMapper.toEntity(dto)));
        return salaryIncreaseMapper.toResponse(salaryIncreaseDTO);
    }
 
    @Transactional
    @Override
    public SalaryIncreaseResponse updateSalaryIncrease(Integer id, SalaryIncreaseDTO dto) throws DataNotFoundException {
        if (!id.equals(dto.getId())) {
            throw new InvalidStatusException("Id not match");
        }
        eventFormService.updateEventForm(dto.getEventFormDTO().getId(), dto.getEventFormDTO());
        SalaryIncreaseDTO salaryIncreaseDTO = salaryIncreaseMapper.toDTO(salaryIncreaseRepository.save(salaryIncreaseMapper.toEntity(dto)));
        return salaryIncreaseMapper.toResponse(salaryIncreaseDTO);
    }
}
