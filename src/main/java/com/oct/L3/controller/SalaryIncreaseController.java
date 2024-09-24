package com.oct.L3.controller;

import com.oct.L3.Response.ResponseObject;
import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.SalaryIncreaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/salary-increase")
@RequiredArgsConstructor
public class SalaryIncreaseController {
    private final SalaryIncreaseService salaryIncreaseService;
    private final EventFormService eventFormService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createSalaryIncrease(@RequestBody @Valid SalaryIncreaseDTO salaryIncreaseDTO
                                                               , BindingResult result) {
        if (result.hasErrors()) {
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
            SalaryIncreaseDTO salaryIncreaseResult = salaryIncreaseService.createSalaryIncrease(salaryIncreaseDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Salary increase created successfully")
                    .status(HttpStatus.CREATED)
                    .data(salaryIncreaseResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Salary increase creation failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateSalaryIncrease(@PathVariable Integer id, @RequestBody @Valid SalaryIncreaseDTO salaryIncreaseDTO
                                                               , BindingResult result) {
        if (result.hasErrors()) {
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
            SalaryIncreaseDTO salaryIncreaseResult = salaryIncreaseService.updateSalaryIncrease(id, salaryIncreaseDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Salary increase updated successfully")
                    .status(HttpStatus.OK)
                    .data(salaryIncreaseResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Salary increase update failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }

}
