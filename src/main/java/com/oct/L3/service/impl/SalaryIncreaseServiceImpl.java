package com.oct.L3.service.impl;

import com.oct.L3.convertTo.SalaryIncreaseMapper;
import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.entity.EventForm;
import com.oct.L3.entity.SalaryIncrease;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.repository.SalaryIncreaseRepository;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.SalaryIncreaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SalaryIncreaseServiceImpl implements SalaryIncreaseService {

    private final SalaryIncreaseRepository salaryIncreaseRepository;
    private final EventFormService eventFormService;
    private final SalaryIncreaseMapper salaryIncreaseMapper;

    @Transactional
    @Override
    public SalaryIncreaseDTO createSalaryIncrease(SalaryIncreaseDTO salaryIncreaseDTO) {
        if (salaryIncreaseDTO.getEventForm() == null) {
            throw new RuntimeException("EventForm is required");
        }
        salaryIncreaseRepository.save(salaryIncreaseMapper.toEntity(salaryIncreaseDTO));
        return null;
    }

    @Transactional
    @Override
    public SalaryIncreaseDTO updateSalaryIncrease(Integer id, SalaryIncreaseDTO salaryIncreaseDTO) throws DataNotFoundException {
        eventFormService.updateEventForm( id, salaryIncreaseDTO.getEventForm());
        salaryIncreaseRepository.save(salaryIncreaseMapper.toEntity(salaryIncreaseDTO));
        return null;
    }

    @Override
    public SalaryIncreaseDTO getSalaryIncreaseById(Integer id) throws DataNotFoundException {
        SalaryIncrease salaryIncrease = salaryIncreaseRepository.findByEventForm(id);
        if (salaryIncrease == null) {
            throw new DataNotFoundException("SalaryIncrease not found");
        }
        return salaryIncreaseMapper.toDTO(salaryIncrease);
    }

}
