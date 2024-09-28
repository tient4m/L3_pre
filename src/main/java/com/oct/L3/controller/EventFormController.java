package com.oct.L3.controller;

import com.oct.L3.Response.*;
import com.oct.L3.convertTo.EventFormMapper;
import com.oct.L3.convertTo.PromotionMapper;
import com.oct.L3.convertTo.ProposalMapper;
import com.oct.L3.convertTo.SalaryIncreaseMapper;
import com.oct.L3.dtos.EventForm.EventFormDTO;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.PromotionService;
import com.oct.L3.service.ProposalService;
import com.oct.L3.service.SalaryIncreaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import static com.oct.L3.constant.Status.*;
import static com.oct.L3.constant.EventType.*;

import java.util.Date;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/event-form")
public class EventFormController {

    private final EventFormService eventFormService;
    private final PromotionMapper promotionMapper;
    private final SalaryIncreaseMapper salaryIncreaseMapper;
    private final SalaryIncreaseService salaryIncreaseService;
    private final PromotionService promotionService;
    private final ProposalMapper proposalMapper;
    private final ProposalService proposalService;

    @PostMapping("termination-request")
    public ResponseEntity<ResponseObject> createTerminationRequest(
            @Valid @RequestBody EventFormDTO eventFormDTO,
            BindingResult result
    ) {
        if(result.hasErrors()) {
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
        eventFormDTO.setType(TERMINATION_REQUEST);
        eventFormDTO.setStatus(DRAFT);
        try {
            EventFormDTO empResult = eventFormService.saveEventForm(eventFormDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee termination request successful")
                    .status(HttpStatus.CREATED)
                    .data(empResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee termination request failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }

    @PostMapping("register")
    public ResponseEntity<ResponseObject> registerEmployee(
            @Valid @RequestBody EventFormDTO eventFormDTO,
            BindingResult result
    ) {
        if(result.hasErrors()) {
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
        eventFormDTO.setType(REGISTRATION);
        eventFormDTO.setStatus(DRAFT);
        try {

            EventFormDTO empResult = eventFormService.saveEventForm(eventFormDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee registration successful")
                    .status(HttpStatus.CREATED)
                    .data(empResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee registration failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ResponseObject> updateEmployee(
            @Valid @RequestBody EventFormDTO eventFormDTO,
            @PathVariable Integer id,
            BindingResult result
    ) {
        if(result.hasErrors()) {
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

            EventFormDTO empResult = eventFormService.updateEventForm(id,eventFormDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee updated successful")
                    .status(HttpStatus.OK)
                    .data(empResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee registration failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }

    @PatchMapping("send/{leaderId}/{eventFormId}")
    public ResponseEntity<ResponseObject> sendToLeader(
            @PathVariable Integer leaderId,
            @PathVariable Integer eventFormId,
            @RequestParam String content,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date submissionDate
    ) {
        try {

            EventFormDTO empResult = eventFormService.sendFormToLeader(leaderId,eventFormId,content,submissionDate);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee sent to leader successful")
                    .status(HttpStatus.OK)
                    .data(empResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee sent to leader failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }

    @PatchMapping("rejected/{leaderId}/{eventFormId}")
    public ResponseEntity<ResponseObject> rejectEmployee(
            @PathVariable Integer leaderId,
            @PathVariable Integer eventFormId,
            @RequestParam String content,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date submissionDate
    ) {
        try {
            EventFormDTO empResult = eventFormService.updateEventFormStatus(leaderId,eventFormId,submissionDate,content,REJECTED);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee rejected successful")
                    .status(HttpStatus.OK)
                    .data(empResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee rejected failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }

    @PatchMapping("approve/{leaderId}/{eventFormId}")
    public ResponseEntity<ResponseObject> approveEmployee(
            @PathVariable Integer leaderId,
            @PathVariable Integer eventFormId,
            @RequestParam String content,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date submissionDate
    ) {
        try {
            EventFormDTO empResult = eventFormService.updateEventFormStatus(eventFormId,leaderId,submissionDate,content,APPROVED);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee approved successful")
                    .status(HttpStatus.OK)
                    .data(empResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee approved failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> getEmployee(@PathVariable Integer id) {
        try {
            EventFormDTO empResult = eventFormService.getEventFormById(id);
            switch (empResult.getType()) {
                case SALARYINCREASE:
                    SalaryIncreaseResponse salaryIncreaseResponse = salaryIncreaseMapper.toResponse(salaryIncreaseService.getSalaryIncreaseByEventFormId(empResult.getId()));
                    EventFormResponse<SalaryIncreaseResponse> eventFormSalaryIncreaseResponse = EventFormResponse.<SalaryIncreaseResponse>builder()
                            .eventFormId(empResult.getId())
                            .employee(empResult.getEmployeeData())
                            .type(empResult.getType())
                            .date(empResult.getDate())
                            .submissionDate(empResult.getSubmissionDate())
                            .content(empResult.getContent())
                            .status(empResult.getStatus())
                            .note(empResult.getNote())
                            .formDetails(salaryIncreaseResponse)
                            .histories(empResult.getHistories())
                            .build();
                    return ResponseEntity.ok().body(ResponseObject.builder()
                            .message("Employee retrieved successful")
                            .status(HttpStatus.OK)
                            .data(eventFormSalaryIncreaseResponse)
                            .build());
                case PROMOTION:
                    PromotionResponse promotionResponse = promotionMapper.toResponse(promotionService.getPromotionById(empResult.getId()));
                    EventFormResponse<PromotionResponse> eventFormPromotionResponse = EventFormResponse.<PromotionResponse>builder()
                            .eventFormId(empResult.getId())
                            .employee(empResult.getEmployeeData())
                            .type(empResult.getType())
                            .date(empResult.getDate())
                            .submissionDate(empResult.getSubmissionDate())
                            .content(empResult.getContent())
                            .status(empResult.getStatus())
                            .note(empResult.getNote())
                            .formDetails(promotionResponse)
                            .histories(empResult.getHistories())
                            .build();
                    return ResponseEntity.ok().body(ResponseObject.builder()
                            .message("Employee retrieved successful")
                            .status(HttpStatus.OK)
                            .data(eventFormPromotionResponse)
                            .build());
                case PROPOSAL:
                    ProposalResponse proposalResponse = proposalMapper.toResponse(proposalService.getProposalByEventFormId(empResult.getId()));
                    EventFormResponse<ProposalResponse> eventFormProposalResponse = EventFormResponse.<ProposalResponse>builder()
                            .eventFormId(empResult.getId())
                            .employee(empResult.getEmployeeData())
                            .type(empResult.getType())
                            .date(empResult.getDate())
                            .submissionDate(empResult.getSubmissionDate())
                            .content(empResult.getContent())
                            .status(empResult.getStatus())
                            .note(empResult.getNote())
                            .formDetails(proposalResponse)
                            .histories(empResult.getHistories())
                            .build();
                    return ResponseEntity.ok().body(ResponseObject.builder()
                            .message("Employee retrieved successful")
                            .status(HttpStatus.OK)
                            .data(eventFormProposalResponse)
                            .build());
                    default:
                    return ResponseEntity.ok().body(ResponseObject.builder()
                            .message("Employee retrieved successful")
                            .status(HttpStatus.OK)
                            .data(empResult)
                            .build());
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee retrieved failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }

    @PatchMapping("additional-requirements/{leaderId}/{eventFormId}")
    public ResponseEntity<ResponseObject> additionalRequirements(
            @PathVariable Integer leaderId,
            @PathVariable Integer eventFormId,
            @RequestParam String content,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date submissionDate
    ) {
        try {
            EventFormDTO empResult = eventFormService.updateEventFormStatus(eventFormId,leaderId,submissionDate,content,ADDITIONAL_REQUIREMENTS);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee additional requirements successful")
                    .status(HttpStatus.OK)
                    .data(empResult)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Employee additional requirements failed" + e)
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }
}
