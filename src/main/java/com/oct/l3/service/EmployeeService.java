package com.oct.l3.service;

import com.oct.l3.dtos.EmployeeDTO;
import com.oct.l3.dtos.request.EmployeeRegistrationRequest;
import com.oct.l3.dtos.response.EmployeeRegistrationResponse;
import com.oct.l3.exceptions.DataNotFoundException;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAll();

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO) throws DataNotFoundException;

    EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO);

    EmployeeRegistrationResponse registrationEmployee(EmployeeRegistrationRequest request);
}
