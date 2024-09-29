package com.oct.L3.controller;

import com.oct.L3.Response.ResponseObject;
import com.oct.L3.dtos.ProposalDTO;
import com.oct.L3.service.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static com.oct.L3.constant.EventType.PROPOSAL;

@RestController
@RequestMapping("${api.prefix}/proposal")
@RequiredArgsConstructor
public class ProposalController {

    private final ProposalService proposalService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createProposal(@RequestBody @Valid ProposalDTO proposalDTO
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
//            String type = proposalDTO.getType();
//            type.toUpperCase();
            proposalDTO.getEventForm().setType(PROPOSAL);
            ProposalDTO proposalResult = proposalService.createProposal(proposalDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Proposal created successfully")
                    .status(HttpStatus.CREATED)
                    .data(proposalResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Proposal creation failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateProposal(
            @PathVariable Integer evenFormId,
            @RequestBody @Valid ProposalDTO proposalDTO,
            BindingResult result) {
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
            ProposalDTO proposalResult = proposalService.updateProposal(evenFormId,proposalDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Proposal updated successfully")
                    .status(HttpStatus.OK)
                    .data(proposalResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Proposal update failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }
}
