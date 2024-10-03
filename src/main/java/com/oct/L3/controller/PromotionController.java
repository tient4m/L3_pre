package com.oct.L3.controller;

import com.oct.L3.dtos.response.ResponseObject;
import com.oct.L3.dtos.PromotionDTO;
import com.oct.L3.service.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.oct.L3.constant.EventType.PROMOTION;

@RestController
@RequestMapping("${api.prefix}/promotion")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createPromotion(@RequestBody @Valid PromotionDTO promotionDTO) {
        try {
            PromotionDTO promotionResult = promotionService.createPromotion(promotionDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("PromotionEntity created successfully")
                    .status(HttpStatus.CREATED)
                    .data(promotionResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("PromotionEntity creation failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updatePromotion(@PathVariable Integer id,
                                                          @RequestBody @Valid PromotionDTO promotionDTO) {
        try {
            PromotionDTO promotionResult = promotionService.updatePromotion(id, promotionDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("PromotionEntity updated successfully")
                    .status(HttpStatus.OK)
                    .data(promotionResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("PromotionEntity update failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }
}
