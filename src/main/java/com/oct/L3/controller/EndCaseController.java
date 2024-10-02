package com.oct.L3.controller;

import com.oct.L3.Response.ResponseObject;
import com.oct.L3.dtos.EndCaseDTO;
import com.oct.L3.service.EndCaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/endcase")
@RequiredArgsConstructor
public class EndCaseController {

    private final EndCaseService endCaseService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("")
    public ResponseEntity<ResponseObject> createEndCase(@RequestBody @Valid EndCaseDTO endCaseDTO) {
        try {
            EndCaseDTO endCase = endCaseService.createEndCase(endCaseDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("End case created successfully")
                    .status(HttpStatus.CREATED)
                    .data(endCase)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }
    }
}
