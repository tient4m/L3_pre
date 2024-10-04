package com.oct.L3.service;

import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.dtos.request.EmployeeRegistrationRequest;
import com.oct.L3.dtos.response.EmployeeRegistrationRespon;
import com.oct.L3.exceptions.DataNotFoundException;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAll();

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO) throws DataNotFoundException;

    EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO);

    EmployeeRegistrationRespon registrationEmployee(EmployeeRegistrationRequest request);
}
