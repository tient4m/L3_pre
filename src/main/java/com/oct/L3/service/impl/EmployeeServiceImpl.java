package com.oct.L3.service.impl;

import com.oct.L3.convertTo.EmployeeMapper;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.entity.*;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    @Override
    public List<EmployeeDTO> getAll() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        return employeeMapper.toDTO(employeeRepository.save(employee));
    }







}
