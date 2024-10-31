package com.oct.l3.service;

import com.oct.l3.dtos.request.EmployeeRequest;
import com.oct.l3.dtos.request.EmployeeRegistrationRequest;
import com.oct.l3.dtos.response.EmployeeRegistrationResponse;
import com.oct.l3.exceptions.DataNotFoundException;

import java.util.List;

public interface EmployeeService {
    List<EmployeeRequest> getAll();

    EmployeeRequest createEmployee(EmployeeRequest employeeRequest) throws DataNotFoundException;

    EmployeeRequest updateEmployee(Integer id, EmployeeRequest employeeRequest);

    EmployeeRegistrationResponse registrationEmployee(EmployeeRegistrationRequest request);
}
