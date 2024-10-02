package com.oct.L3.service.impl;

import com.oct.L3.convertTo.SalaryIncreaseMapper;
import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.entity.EventForm;
import com.oct.L3.entity.SalaryIncrease;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.repository.EventFormRepository;
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
    private final EventFormRepository eventFormRepository;


    @Transactional
    @Override
    public SalaryIncreaseDTO createSalaryIncrease(SalaryIncreaseDTO salaryIncreaseDTO) {
        SalaryIncrease salaryIncrease = salaryIncreaseMapper.toEntity(salaryIncreaseDTO);
        if (!salaryIncrease.getEventForm().getEmployee().getStatus().equals(ACTIVE)) {
            throw new RuntimeException("Employee is not active");
        }
        if (salaryIncreaseDTO.getEventForm() == null) {
            throw new RuntimeException("EventForm is required");
        }
        return salaryIncreaseMapper.toDTO(salaryIncreaseRepository.save(salaryIncrease));
    }
 
    @Transactional
    @Override
    public SalaryIncreaseDTO updateSalaryIncrease(Integer id, SalaryIncreaseDTO salaryIncreaseDTO) throws DataNotFoundException {
        eventFormService.updateEventForm(id, salaryIncreaseDTO.getEventForm());
        EventForm updatedEventForm = eventFormRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("EventForm not found"));
        SalaryIncrease salaryIncrease = salaryIncreaseMapper.toEntity(salaryIncreaseDTO);
        salaryIncrease.setEventForm(updatedEventForm);
        salaryIncrease = salaryIncreaseRepository.save(salaryIncrease);
        return salaryIncreaseMapper.toDTO(salaryIncrease);
    }

    @Override
    public SalaryIncreaseDTO getSalaryIncreaseByEventFormId(Integer id) throws DataNotFoundException {
        SalaryIncrease salaryIncrease = salaryIncreaseRepository.findByEventForm(id);
        if (salaryIncrease == null) {
            throw new DataNotFoundException("SalaryIncrease not found");
        }
        return salaryIncreaseMapper.toDTO(salaryIncrease);
    }

}
