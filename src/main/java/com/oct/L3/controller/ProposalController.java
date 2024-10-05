package com.oct.L3.controller;

import com.oct.L3.dtos.response.ResponseObject;
import com.oct.L3.dtos.ProposalDTO;
import com.oct.L3.service.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/proposal")
@RequiredArgsConstructor
public class ProposalController {

    private final ProposalService proposalService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createProposal(@RequestBody @Valid ProposalDTO proposalDTO) {
            ProposalDTO proposalResult = proposalService.createProposal(proposalDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("ProposalEntity created successfully")
                    .status(HttpStatus.CREATED)
                    .data(proposalResult)
                    .build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateProposal(
            @PathVariable Integer id,
            @RequestBody @Valid ProposalDTO proposalDTO) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("ProposalEntity updated successfully")
                    .status(HttpStatus.OK)
                    .data(proposalService.updateProposal(id,proposalDTO))
                    .build());
    }
}
