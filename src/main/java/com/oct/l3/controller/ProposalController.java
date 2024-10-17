package com.oct.l3.controller;

import com.oct.l3.dtos.response.ResponseObject;
import com.oct.l3.dtos.ProposalDTO;
import com.oct.l3.service.ProposalService;
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
    @PostMapping("")
    public ResponseEntity<ResponseObject> createProposal(@RequestBody @Valid ProposalDTO proposalDTO) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("ProposalEntity created successfully")
                    .status(HttpStatus.CREATED)
                    .data(proposalService.createProposal(proposalDTO))
                    .build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("{id}")
    public ResponseEntity<ResponseObject> updateProposal(
            @PathVariable Integer id,
            @RequestBody @Valid ProposalDTO proposalDTO) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("ProposalEntity updated successfully")
                    .status(HttpStatus.OK)
                    .data(proposalService.updateProposal(id, proposalDTO))
                    .build());
    }
}
