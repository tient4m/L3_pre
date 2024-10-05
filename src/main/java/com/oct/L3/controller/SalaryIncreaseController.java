package com.oct.L3.controller;

import com.oct.L3.dtos.response.ResponseObject;
import com.oct.L3.components.JWTTokenUtil;
import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.SalaryIncreaseService;
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
    private final EventFormService eventFormService;
    private final JWTTokenUtil jwtTokenUtil;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createSalaryIncrease(@RequestBody @Valid SalaryIncreaseDTO salaryIncreaseDTO) {
            SalaryIncreaseDTO salaryIncreaseResult = salaryIncreaseService.createSalaryIncrease(salaryIncreaseDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Salary increase created successfully")
                    .status(HttpStatus.CREATED)
                    .data(salaryIncreaseResult)
                    .build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateSalaryIncrease(@PathVariable Integer id,
                                                               @RequestBody @Valid SalaryIncreaseDTO salaryIncreaseDTO) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Salary increase updated successfully")
                    .status(HttpStatus.OK)
                    .data(salaryIncreaseService.updateSalaryIncrease(id, salaryIncreaseDTO))
                    .build());
    }

}
