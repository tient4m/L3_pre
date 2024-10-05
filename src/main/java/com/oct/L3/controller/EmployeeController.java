package com.oct.L3.controller;

import com.oct.L3.dtos.request.EmployeeRegistrationRequest;
import com.oct.L3.dtos.response.ResponseObject;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllEmployees() {
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("All employees fetched successfully")
                .status(HttpStatus.OK)
                .data(employeeService.getAll())
                .build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("")
    public ResponseEntity<ResponseObject> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity saved successfully")
                    .status(HttpStatus.OK)
                    .data(employeeService.createEmployee(employeeDTO))
                    .build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateEmployee(@PathVariable Integer id,
                                                         @RequestBody @Valid EmployeeDTO employeeDTO
    ) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity updated successfully")
                    .status(HttpStatus.OK)
                    .data(employeeService.updateEmployee(id, employeeDTO))
                    .build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerEmployee(@RequestBody @Valid EmployeeRegistrationRequest request) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity registered successfully")
                    .status(HttpStatus.OK)
                    .data(employeeService.registrationEmployee(request))
                    .build());
    }



}
