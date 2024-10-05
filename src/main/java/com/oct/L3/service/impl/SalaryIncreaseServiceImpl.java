package com.oct.L3.service.impl;

import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.entity.SalaryIncreaseEntity;
import com.oct.L3.mapper.SalaryIncreaseMapper;
import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.repository.SalaryIncreaseRepository;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.SalaryIncreaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oct.L3.constant.EventType.SALARY_INCREASE;
import static com.oct.L3.constant.Status.ACTIVE;
import static com.oct.L3.constant.Status.DRAFT;

@Service
@RequiredArgsConstructor
public class SalaryIncreaseServiceImpl implements SalaryIncreaseService {

    private final SalaryIncreaseRepository salaryIncreaseRepository;
    private final EventFormService eventFormService;
    private final SalaryIncreaseMapper salaryIncreaseMapper;
    private final EmployeeRepository employeeRepository;


    @Transactional
    @Override
    public SalaryIncreaseDTO createSalaryIncrease(SalaryIncreaseDTO salaryIncreaseDTO) {
        if (salaryIncreaseDTO.getEventFormDTO() == null) {
            throw new RuntimeException("EventFormEntity is required");
        }
        salaryIncreaseDTO.getEventFormDTO().setType(SALARY_INCREASE);
        eventFormService.createEventForm(salaryIncreaseDTO.getEventFormDTO());
        return salaryIncreaseMapper.toDTO(salaryIncreaseRepository.save(salaryIncreaseMapper.toEntity(salaryIncreaseDTO)));
    }
 
    @Transactional
    @Override
    public SalaryIncreaseDTO updateSalaryIncrease(Integer id, SalaryIncreaseDTO salaryIncreaseDTO) throws DataNotFoundException {
        if (id.equals(salaryIncreaseDTO.getId())) {
            throw new RuntimeException("Id not match");
        }
        eventFormService.updateEventForm(salaryIncreaseDTO.getEventFormDTO().getId(), salaryIncreaseDTO.getEventFormDTO());
        return salaryIncreaseMapper.toDTO(salaryIncreaseRepository.save(salaryIncreaseMapper.toEntity(salaryIncreaseDTO)));
    }

    @Override
    public SalaryIncreaseDTO getSalaryIncreaseByEventFormId(Integer id) throws DataNotFoundException {
        SalaryIncreaseEntity salaryIncreaseEntity = salaryIncreaseRepository.findByEventFormId(id);
        if (salaryIncreaseEntity == null) {
            throw new DataNotFoundException("SalaryIncreaseEntity not found");
        }
        return salaryIncreaseMapper.toDTO(salaryIncreaseEntity);
    }

}
