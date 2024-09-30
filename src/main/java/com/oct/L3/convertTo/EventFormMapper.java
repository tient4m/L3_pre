package com.oct.L3.convertTo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oct.L3.Response.EventFormResponse;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.dtos.EventForm.EventFormDTO;
import com.oct.L3.dtos.EventFormHistoryDTO;
import com.oct.L3.entity.Employee;
import com.oct.L3.entity.EventForm;
import com.oct.L3.entity.EventFormHistory;
import com.oct.L3.entity.User;
import com.oct.L3.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventFormMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EventFormDTO toDTO(EventForm eventForm) {
        EventFormDTO eventFormDTO = EventFormDTO.builder()
                .Id(eventForm.getId())
                .leaderComments(eventForm.getLeaderComments())
                .managerComments(eventForm.getManagerComments())
                .type(eventForm.getType())
                .date(eventForm.getDate())
                .submissionDate(eventForm.getSubmissionDate())
                .content(eventForm.getContent())
                .status(eventForm.getStatus())
                .note(eventForm.getNote())
                .build();
        if (eventForm.getEmployee() != null) {
            eventFormDTO.setEmployeeId(eventForm.getEmployee().getId());
        }else {
            eventFormDTO.setEmployeeId(null);
        }

        if (eventForm.getEmployee() != null) {
            eventFormDTO.setEmployeeId(eventForm.getEmployee().getId());
        }
        if (eventForm.getManager() != null) {
            eventFormDTO.setManagerId(eventForm.getManager().getId());
        }
        if (eventForm.getLeader() != null) {
            eventFormDTO.setLeaderId(eventForm.getLeader().getId());
        }
        if (eventForm.getHistories() != null) {
            eventFormDTO.setHistories(eventForm.getHistories().stream()
                    .map(eventFormHistory -> modelMapper.map(eventFormHistory, EventFormHistoryDTO.class))
                    .collect(Collectors.toList()));
        }
        return eventFormDTO;
    }

    public EventForm toEntity(EventFormDTO dto) {

        EventForm eventForm = EventForm.builder()
                .Id(dto.getId())
                .leaderComments(dto.getLeaderComments())
                .managerComments(dto.getManagerComments())
                .type(dto.getType())
                .date(dto.getDate())
                .submissionDate(dto.getSubmissionDate())
                .content(dto.getContent())
                .status(dto.getStatus())
                .note(dto.getNote())
//                .employee(employeeRepository.findById(dto.getEmployeeId()).orElse(null))
                .build();
        if (dto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElse(null);
            eventForm.setEmployee(employee);
        }else {
            eventForm.setEmployee(null);
        }

        if (dto.getLeaderId() != null) {
            User leader = new User();
            leader.setId(dto.getLeaderId());
            eventForm.setLeader(leader);
        }
        if (dto.getManagerId() != null) {
            User manager = new User();
            manager.setId(dto.getManagerId());
            eventForm.setManager(manager);
        }
        if (dto.getHistories() != null && !dto.getHistories().isEmpty()) {
            eventForm.setHistories(dto.getHistories().stream()
                    .map(history -> modelMapper.map(history, EventFormHistory.class))
                    .collect(Collectors.toList()));
        }
        return eventForm;
    }

    public  EventFormResponse toResponse(EventFormDTO dto) {
        return EventFormResponse.builder()
                .eventFormId(dto.getId())
                .employee(employeeMapper.toDTO(
                        employeeRepository
                                .findById(dto.getEmployeeId())
                                .orElse(null)
                        )
                )
                .type(dto.getType())
                .date(dto.getDate())
                .submissionDate(dto.getSubmissionDate())
                .content(dto.getContent())
                .status(dto.getStatus())
                .note(dto.getNote())
                .build();
    }

    public EventForm mergerEntityAndDTO(EventForm eventForm, EventFormDTO dto) {
        if (dto.getLeaderComments() != null && dto.getLeaderComments().equals(eventForm.getLeaderComments())) {
            eventForm.setLeaderComments(dto.getLeaderComments());
        }
        if (dto.getManagerComments() != null) {
            eventForm.setManagerComments(dto.getManagerComments());
        }
        if (dto.getType() != null) {
            eventForm.setType(dto.getType());
        }
        if (dto.getDate() != null) {
            eventForm.setDate(dto.getDate());
        }
        if (dto.getSubmissionDate() != null) {
            eventForm.setSubmissionDate(dto.getSubmissionDate());
        }
        if (dto.getContent() != null) {
            eventForm.setContent(dto.getContent());
        }
        if (dto.getStatus() != null) {
            eventForm.setStatus(dto.getStatus());
        }

        if (dto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElse(null);
            eventForm.setEmployee(employee);
        }

        if (dto.getManagerId() != null) {
            User manager = new User();
            manager.setId(dto.getManagerId());
            eventForm.setManager(manager);
        }
        if (dto.getNote() != null ){
            eventForm.setNote(dto.getNote());
        }

        if (dto.getLeaderId() != null) {
            User leader = new User();
            leader.setId(dto.getLeaderId());
            eventForm.setLeader(leader);
        }

        if (dto.getHistories() != null && !dto.getHistories().isEmpty()) {
            eventForm.setHistories(dto.getHistories().stream()
                    .map(history -> modelMapper.map(history, EventFormHistory.class))
                    .collect(Collectors.toList()));
        }
        return eventForm;
    }
}

