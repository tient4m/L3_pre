package com.oct.L3.service.impl;

import com.oct.L3.dtos.EndCaseDTO;
import com.oct.L3.entity.Employee;
import com.oct.L3.entity.EndCase;
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


    @Override
    public EndCaseDTO createEndCase(EndCaseDTO endCaseDTO) {
        Employee employee = employeeRepository.findById(endCaseDTO.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));
        if (!employee.getStatus().equals(TERMINATED)){
            throw new IllegalStateException("Employee is not terminated");
        }
        EndCase endCase = EndCase.builder()
                .employee(employee)
                .decision_date(endCaseDTO.getDecision_date())
                .archiveNumber(endCaseDTO.getArchiveNumber())
                .status(ARCHIVED)
                .build();
        employee.setStatus(ARCHIVED);
        endCaseRepository.save(endCase);
        return EndCaseDTO.builder()
                .endCaseId(endCase.getId())
                .employeeId(endCase.getEmployee().getId())
                .decision_date(endCase.getDecision_date())
                .archiveNumber(endCase.getArchiveNumber())
                .status(endCase.getStatus())
                .build();
    }
}
