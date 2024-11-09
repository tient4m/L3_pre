package com.example.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeServiceClient employeeServiceClient;

    public ResponseObject getAllEmployees() {
        return employeeServiceClient.getAllEmployees();
    }

}
