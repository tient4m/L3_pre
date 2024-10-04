package com.oct.L3.controller;

import com.oct.L3.dtos.request.SendToLeaderDTO;
import com.oct.L3.dtos.response.*;
import com.oct.L3.mapper.EventFormMapper;
import com.oct.L3.mapper.PromotionMapper;
import com.oct.L3.mapper.ProposalMapper;
import com.oct.L3.mapper.SalaryIncreaseMapper;
import com.oct.L3.dtos.EventFormDTO;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.PromotionService;
import com.oct.L3.service.ProposalService;
import com.oct.L3.service.SalaryIncreaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.oct.L3.constant.Status.*;
import static com.oct.L3.constant.EventType.*;

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
    private final EventFormMapper eventFormMapper;

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("termination-request")
    public ResponseEntity<ResponseObject> createTerminationRequest(@Valid @RequestBody EventFormDTO eventFormDTO) {
        eventFormDTO.setType(TERMINATION_REQUEST);
            EventFormDTO empResult = eventFormService.createEventForm(eventFormDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity termination request successful")
                    .status(HttpStatus.CREATED)
                    .data(empResult)
                    .build());
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("register")
    public ResponseEntity<ResponseObject> registerEmployee(
            @Valid @RequestBody EventFormDTO eventFormDTO
    ) {
            eventFormDTO.setType(REGISTRATION);
            EventFormDTO empResult = eventFormService.createEventForm(eventFormDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity registration successful")
                    .status(HttpStatus.CREATED)
                    .data(empResult)
                    .build());
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PutMapping("update/{id}")
    public ResponseEntity<ResponseObject> updateEmployee(
            @Valid @RequestBody EventFormDTO eventFormDTO,
            @PathVariable Integer id
    ) {

            EventFormDTO empResult = eventFormService.updateEventForm(id,eventFormDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity updated successful")
                    .status(HttpStatus.OK)
                    .data(empResult)
                    .build());
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping ("send-to-leader")
    public ResponseEntity<ResponseObject> sendToLeader(
            @RequestBody SendToLeaderDTO sendToLeaderDTO
            ) {

            EventFormDTO empResult = eventFormService.sendFormToLeader(
                    sendToLeaderDTO.getLeaderId(),
                    sendToLeaderDTO.getEventFormId(),
                    sendToLeaderDTO.getSubmissionDate(),
                    sendToLeaderDTO.getManagerComments()
            );

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity sent to leaderId successful")
                    .status(HttpStatus.OK)
                    .data(empResult)
                    .build());
    }

    @PreAuthorize("hasRole('ROLE_LEADER')")
    @PostMapping("rejected")
    public ResponseEntity<ResponseObject> rejectEmployee(
            @RequestBody Integer eventFormId,
            @RequestBody String leaderComments
    ) {
            EventFormDTO empResult = eventFormService.updateEventFormStatus(eventFormId,leaderComments,REJECTED);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity rejected successful")
                    .status(HttpStatus.OK)
                    .data(empResult)
                    .build());
    }

    @PreAuthorize("hasRole('ROLE_LEADER')")
    @PostMapping("approve")
    public ResponseEntity<ResponseObject> approveEmployee(
            @RequestBody Integer eventFormId,
            @RequestBody String leaderComments
    ) {
            EventFormDTO empResult = eventFormService.updateEventFormStatus(eventFormId,leaderComments,APPROVED);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity approved successful")
                    .status(HttpStatus.OK)
                    .data(empResult)
                    .build());
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_LEADER')")

    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> getEmployee(@PathVariable Integer id) {
            EventFormDTO empResult = eventFormService.getEventFormById(id);
            switch (empResult.getType()) {
                case SALARYINCREASE:
                    SalaryIncreaseResponse salaryIncreaseResponse = salaryIncreaseMapper.toResponse(salaryIncreaseService.getSalaryIncreaseByEventFormId(empResult.getId()));
                    salaryIncreaseResponse.setEventForm(empResult);
                    return ResponseEntity.ok().body(ResponseObject.builder()
                            .message("EmployeeEntity retrieved successful")
                            .status(HttpStatus.OK)
                            .data(salaryIncreaseResponse)
                            .build());
                case PROMOTION:
                    PromotionResponse promotionResponse = promotionMapper.toResponse(promotionService.getPromotionById(empResult.getId()));
                    promotionResponse.setEventForm(empResult);
                    return ResponseEntity.ok().body(ResponseObject.builder()
                            .message("EmployeeEntity retrieved successful")
                            .status(HttpStatus.OK)
                            .data(promotionResponse)
                            .build());
                case PROPOSAL:
                    ProposalResponse proposalResponse = proposalMapper.toResponse(proposalService.getProposalByEventFormId(empResult.getId()));
                    proposalResponse.setEventForm(empResult);
                    return ResponseEntity.ok().body(ResponseObject.builder()
                            .message("EmployeeEntity retrieved successful")
                            .status(HttpStatus.OK)
                            .data(proposalResponse)
                            .build());
                    default:
                        EventFormResponse eventFormResponse  = eventFormMapper.toResponse(empResult);
                    return ResponseEntity.ok().body(ResponseObject.builder()
                            .message("EmployeeEntity retrieved successful")
                            .status(HttpStatus.OK)
                            .data(eventFormResponse)
                            .build());
            }
    }

    @PreAuthorize("hasRole('ROLE_LEADER')")
    @PostMapping("additional-requirements")
    public ResponseEntity<ResponseObject> additionalRequirements(
            @RequestBody Integer eventFormId,
            @RequestBody String leaderComments
    ) {         EventFormDTO empResult = eventFormService.updateEventFormStatus(eventFormId,leaderComments,ADDITIONAL_REQUIREMENTS);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity additional requirements successful")
                    .status(HttpStatus.OK)
                    .data(empResult)
                    .build());
    }
}
