package com.oct.L3.controller;

import com.oct.L3.dtos.request.LeaderActionRequest;
import com.oct.L3.dtos.request.SendToLeaderRequest;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.oct.L3.constant.Status.*;
import static com.oct.L3.constant.EventType.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/event-form")
public class EventFormController {

    private final EventFormService eventFormService;
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
            @RequestBody SendToLeaderRequest sendToLeaderRequest
            ) {

            EventFormDTO empResult = eventFormService.sendFormToLeader(
                    sendToLeaderRequest.getLeaderId(),
                    sendToLeaderRequest.getEventFormId(),
                    sendToLeaderRequest.getSubmissionDate(),
                    sendToLeaderRequest.getManagerComments()
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
            @RequestBody LeaderActionRequest leaderActionRequest
    ) {
            EventFormDTO empResult = eventFormService.processEventFormByLeader(leaderActionRequest.getEventFormId(),leaderActionRequest.getLeaderComments(),REJECTED);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity rejected successful")
                    .status(HttpStatus.OK)
                    .data(empResult)
                    .build());
    }

    @PreAuthorize("hasRole('ROLE_LEADER')")
    @PostMapping("approve")
    public ResponseEntity<ResponseObject> approveEmployee(
            @RequestBody LeaderActionRequest leaderActionRequest
    ) {
            EventFormDTO empResult = eventFormService.processEventFormByLeader(leaderActionRequest.getEventFormId(), leaderActionRequest.getLeaderComments(),APPROVED);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity approved successful")
                    .status(HttpStatus.OK)
                    .data(eventFormMapper.toResponse(empResult))
                    .build());
    }

    @PreAuthorize("hasRole('ROLE_LEADER')")
    @PostMapping("additional-requirements")
    public ResponseEntity<ResponseObject> additionalRequirements(
            @RequestBody LeaderActionRequest leaderActionRequest
    ) {         EventFormDTO empResult = eventFormService.processEventFormByLeader(leaderActionRequest.getEventFormId(),leaderActionRequest.getLeaderComments(),ADDITIONAL_REQUIREMENTS);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EmployeeEntity additional requirements successful")
                    .status(HttpStatus.OK)
                    .data(empResult)
                    .build());
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseObject> deleteEmployee(@PathVariable Integer id) {
            eventFormService.deleteEventForm(id);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("EvenForm deleted successful")
                    .status(HttpStatus.OK)
                    .build());
    }
}
