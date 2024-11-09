package com.example.test.service;

import com.example.test.dto.EmployeeRequest;
import com.example.test.dto.ResponseObject;
import com.example.test.client.EmployeeServiceClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeServiceClient employeeServiceClient;
    private final ObjectMapper objectMapper;


    public ResponseObject getAllEmployees() {
        return employeeServiceClient.getAllEmployees();
    }

    public ResponseObject createEmployee(EmployeeRequest employeeRequest) throws JsonProcessingException {
        String employeeRequestJson = objectMapper.writeValueAsString(employeeRequest);
        return employeeServiceClient.createEmployee(employeeRequestJson);
    }

}
