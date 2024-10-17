package com.oct.l3.controller;

import com.oct.l3.dtos.response.ResponseObject;
import com.oct.l3.dtos.SalaryIncreaseDTO;
import com.oct.l3.service.SalaryIncreaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/salary-increase")
@RequiredArgsConstructor
public class SalaryIncreaseController {
    private final SalaryIncreaseService salaryIncreaseService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("")
    public ResponseEntity<ResponseObject> createSalaryIncrease(@RequestBody @Valid SalaryIncreaseDTO salaryIncreaseDTO) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Salary increase created successfully")
                    .status(HttpStatus.CREATED)
                    .data(salaryIncreaseService.createSalaryIncrease(salaryIncreaseDTO))
                    .build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("{id}")
    public ResponseEntity<ResponseObject> updateSalaryIncrease(@PathVariable Integer id,
                                                               @RequestBody @Valid SalaryIncreaseDTO salaryIncreaseDTO) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Salary increase updated successfully")
                    .status(HttpStatus.OK)
                    .data(salaryIncreaseService.updateSalaryIncrease(id, salaryIncreaseDTO))
                    .build());
    }

}
