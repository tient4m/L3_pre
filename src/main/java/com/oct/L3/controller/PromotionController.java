package com.oct.L3.controller;

import com.oct.L3.Response.ResponseObject;
import com.oct.L3.dtos.PromotionDTO;
import com.oct.L3.service.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createPromotion(@RequestBody @Valid PromotionDTO promotionDTO, BindingResult result) {
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
            promotionDTO.getEventForm().setType(PROMOTION);
            PromotionDTO promotionResult = promotionService.createPromotion(promotionDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Promotion created successfully")
                    .status(HttpStatus.CREATED)
                    .data(promotionResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Promotion creation failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updatePromotion(@PathVariable Integer evenFormId, @RequestBody @Valid PromotionDTO promotionDTO, BindingResult result) {
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
            PromotionDTO promotionResult = promotionService.updatePromotion(evenFormId, promotionDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Promotion updated successfully")
                    .status(HttpStatus.OK)
                    .data(promotionResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Promotion update failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }
}
