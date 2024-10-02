package com.oct.L3.controller;

import com.oct.L3.Response.ResponseObject;
import com.oct.L3.configurations.JWTTokenUtil;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import static com.oct.L3.constant.Status.*;
import static com.oct.L3.constant.EventType.*;


import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JWTTokenUtil jwtTokenUtil;

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
    public ResponseEntity<ResponseObject> saveEmployee(@RequestBody @Valid  EmployeeDTO employeeDTO,
                                                       BindingResult result,
                                                       @RequestHeader("Authorization") String authorizationHeader

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
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        Integer managerId = jwtTokenUtil.extractId(token);
        employeeDTO.setManagerId(managerId);
        employeeDTO.setStatus(DRAFT);
        try {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee saved successfully")
                    .status(HttpStatus.OK)
                    .data(employeeService.saveEmployee(employeeDTO))
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
                    .message("Employee updated successfully")
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

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteEmployee(@PathVariable Integer id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee deleted successfully")
                    .status(HttpStatus.OK)
                    .data(null)
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
