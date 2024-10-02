package com.oct.L3.service.impl;

import com.oct.L3.entity.SalaryIncreaseEntity;
import com.oct.L3.mapper.SalaryIncreaseMapper;
import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.repository.SalaryIncreaseRepository;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.SalaryIncreaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oct.L3.constant.Status.ACTIVE;

@Service
@RequiredArgsConstructor
public class SalaryIncreaseServiceImpl implements SalaryIncreaseService {

    private final SalaryIncreaseRepository salaryIncreaseRepository;
    private final EventFormService eventFormService;
    private final SalaryIncreaseMapper salaryIncreaseMapper;


    @Transactional
    @Override
    public SalaryIncreaseDTO createSalaryIncrease(SalaryIncreaseDTO salaryIncreaseDTO) {
        SalaryIncreaseEntity salaryIncreaseEntity = salaryIncreaseMapper.toEntity(salaryIncreaseDTO);
        if (!salaryIncreaseEntity.getEventFormId().getEmployeeId().getStatus().equals(ACTIVE)) {
            throw new RuntimeException("EmployeeEntity is not active");
        }
        if (salaryIncreaseDTO.getEventForm() == null) {
            throw new RuntimeException("EventFormEntity is required");
        }
        return salaryIncreaseMapper.toDTO(salaryIncreaseRepository.save(salaryIncreaseEntity));
    }
 
    @Transactional
    @Override
    public SalaryIncreaseDTO updateSalaryIncrease(Integer id, SalaryIncreaseDTO salaryIncreaseDTO) throws DataNotFoundException {
        eventFormService.updateEventForm( id, salaryIncreaseDTO.getEventForm());
        salaryIncreaseRepository.save(salaryIncreaseMapper.toEntity(salaryIncreaseDTO));
        return null;
    }

    @Override
    public SalaryIncreaseDTO getSalaryIncreaseByEventFormId(Integer id) throws DataNotFoundException {
        SalaryIncreaseEntity salaryIncreaseEntity = salaryIncreaseRepository.findByEventForm(id);
        if (salaryIncreaseEntity == null) {
            throw new DataNotFoundException("SalaryIncreaseEntity not found");
        }
        return salaryIncreaseMapper.toDTO(salaryIncreaseEntity);
    }

}
