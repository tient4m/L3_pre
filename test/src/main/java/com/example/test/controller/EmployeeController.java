package com.example.test.controller;

import com.example.test.dto.EmployeeRequest;
import com.example.test.dto.ResponseObject;
import com.example.test.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

        private final EmployeeService employeeService;

        public EmployeeController(EmployeeService employeeService) {
            this.employeeService = employeeService;
        }

        @GetMapping
        public ResponseObject getAllEmployees() {
            return employeeService.getAllEmployees();
        }

        @PostMapping
        public ResponseObject createEmployee(@RequestBody EmployeeRequest employeeRequest) throws JsonProcessingException {
            return employeeService.createEmployee(employeeRequest);
        }
}
