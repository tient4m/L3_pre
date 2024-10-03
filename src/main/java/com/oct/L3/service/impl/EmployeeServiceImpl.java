package com.oct.L3.service.impl;

import com.oct.L3.mapper.EmployeeMapper;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.entity.*;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.oct.L3.constant.Status.DRAFT;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDTO> getAll() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        employeeDTO.setStatus(DRAFT);
        return employeeMapper.toDTO(employeeRepository.save(employeeMapper.toEntity(employeeDTO)));
    }

    @Override
    public EmployeeDTO updateEmployee(Integer id,EmployeeDTO employeeDTO) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("EmployeeEntity not found");
        }
        if (!Objects.equals(employeeDTO.getId(), id)) {
            throw new RuntimeException("Id not match");
        }
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeDTO);
        return employeeMapper.toDTO(employeeRepository.save(employeeEntity));
    }









}
