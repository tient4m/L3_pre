package com.oct.L3.controller;

import com.oct.L3.dtos.response.ResponseObject;
import com.oct.L3.dtos.PromotionDTO;
import com.oct.L3.service.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/promotion")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createPromotion(@RequestBody @Valid PromotionDTO promotionDTO) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("PromotionEntity created successfully")
                    .status(HttpStatus.CREATED)
                    .data(promotionService.createPromotion(promotionDTO))
                    .build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updatePromotion(@PathVariable Integer id,
                                                          @RequestBody @Valid PromotionDTO promotionDTO) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("PromotionEntity updated successfully")
                    .status(HttpStatus.OK)
                    .data(promotionService.updatePromotion(id, promotionDTO))
                    .build());
    }
}
