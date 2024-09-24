package com.oct.L3.controller;

import com.oct.L3.Response.ResponseObject;
import com.oct.L3.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllEmployees() {
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("All employees fetched successfully")
                .status(HttpStatus.OK)
                .data(employeeService.getAll())
                .build());
    }

}
