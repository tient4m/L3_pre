package com.oct.L3.service.impl;

import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.entity.SalaryIncreaseEntity;
import com.oct.L3.mapper.EventFormMapper;
import com.oct.L3.mapper.SalaryIncreaseMapper;
import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.repository.EventFormRepository;
import com.oct.L3.repository.SalaryIncreaseRepository;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.SalaryIncreaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oct.L3.constant.EventType.SALARYINCREASE;
import static com.oct.L3.constant.Status.ACTIVE;

@Service
@RequiredArgsConstructor
public class SalaryIncreaseServiceImpl implements SalaryIncreaseService {

    private final SalaryIncreaseRepository salaryIncreaseRepository;
    private final EventFormRepository eventFormRepository;
    private final EmployeeRepository employeeRepository;
    private final EventFormService eventFormService;
    private final SalaryIncreaseMapper salaryIncreaseMapper;
    private final EventFormMapper eventFormMapper;


    @Override
    public SalaryIncreaseDTO createSalaryIncrease(SalaryIncreaseDTO salaryIncreaseDTO) {

        SalaryIncreaseEntity salaryIncreaseEntity = salaryIncreaseMapper.toEntity(salaryIncreaseDTO);
        salaryIncreaseDTO.getEventFormDTO().setType(SALARYINCREASE);

        EmployeeEntity employeeEntity = employeeRepository.findById(salaryIncreaseDTO.getEventFormDTO().getEmployeeId())
                .orElseThrow(() -> new RuntimeException("EmployeeEntity not found"));

        if (!employeeEntity.getStatus().equals(ACTIVE)) {
            throw new RuntimeException("EmployeeEntity is not active");
        }
        if (salaryIncreaseDTO.getEventFormDTO() == null) {
            throw new RuntimeException("EventFormEntity is required");
        }

        eventFormRepository.save(eventFormMapper.toEntity(salaryIncreaseDTO.getEventFormDTO()));
        return salaryIncreaseMapper.toDTO(salaryIncreaseRepository.save(salaryIncreaseEntity));
    }

    @Override
    public SalaryIncreaseDTO updateSalaryIncrease(Integer id, SalaryIncreaseDTO salaryIncreaseDTO) throws DataNotFoundException {
        eventFormService.updateEventForm( id, salaryIncreaseDTO.getEventFormDTO());
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
