package com.oct.L3.service;

import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.dtos.EventForm.EmployeeRegistrationDTO;
import com.oct.L3.exceptions.DataNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAll();

    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
}
