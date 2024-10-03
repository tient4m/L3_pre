package com.oct.L3.service;

import com.oct.L3.dtos.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAll();

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO);
}
