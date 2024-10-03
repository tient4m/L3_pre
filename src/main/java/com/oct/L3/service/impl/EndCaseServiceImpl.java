package com.oct.L3.service.impl;

import com.oct.L3.dtos.EndCaseDTO;
import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.mapper.EndCaseMapper;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.repository.EndCaseRepository;
import com.oct.L3.service.EndCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.oct.L3.constant.Status.ARCHIVED;
import static com.oct.L3.constant.Status.TERMINATED;

@Service
@RequiredArgsConstructor
public class EndCaseServiceImpl implements EndCaseService {
    private final EndCaseRepository endCaseRepository;
    private final EmployeeRepository employeeRepository;
    private final EndCaseMapper endCaseMapper;


    @Override
    public EndCaseDTO createEndCase(EndCaseDTO endCaseDTO) {

        EmployeeEntity employeeEntity = employeeRepository.findById(endCaseDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("EmployeeEntity not found"));
        if (!employeeEntity.getStatus().equals(TERMINATED)){
            throw new IllegalStateException("EmployeeEntity is not terminated");
        }

        employeeEntity.setStatus(ARCHIVED);
        employeeRepository.save(employeeEntity);

        return endCaseMapper.toDTO(endCaseRepository.save(endCaseMapper.toEntity(endCaseDTO)));
    }
}
