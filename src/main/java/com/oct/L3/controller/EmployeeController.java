package com.oct.L3.controller;

import com.oct.L3.dtos.response.ResponseObject;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/employee")
public class EmployeeController {


    private EmployeeService employeeService;

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
        try {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity saved successfully")
                    .status(HttpStatus.OK)
                    .data(employeeService.createEmployee(employeeDTO))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }

    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateEmployee(@PathVariable Integer id,
                                                         @RequestBody @Valid EmployeeDTO employeeDTO,
                                                         BindingResult result
    ) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message(errorMessages.toString())
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
        try {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity updated successfully")
                    .status(HttpStatus.OK)
                    .data(employeeService.updateEmployee(id, employeeDTO))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }

}
