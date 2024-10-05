package com.oct.L3.controller;

import com.oct.L3.dtos.response.ResponseObject;
import com.oct.L3.dtos.EndCaseDTO;
import com.oct.L3.service.EndCaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/endcase")
@RequiredArgsConstructor
public class EndCaseController {

    private final EndCaseService endCaseService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("")
    public ResponseEntity<ResponseObject> createEndCase(@RequestBody @Valid EndCaseDTO endCaseDTO){
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("End case created successfully")
                    .status(HttpStatus.CREATED)
                    .data(endCaseService.createEndCase(endCaseDTO))
                    .build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateEndCase(@PathVariable Integer id,
                                                          @RequestBody @Valid EndCaseDTO endCaseDTO) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("End case updated successfully")
                    .status(HttpStatus.OK)
                    .data(endCaseService.update(id, endCaseDTO))
                    .build());
    }
}
