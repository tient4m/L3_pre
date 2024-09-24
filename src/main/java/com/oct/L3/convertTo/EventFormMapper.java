package com.oct.L3.convertTo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oct.L3.Response.EventFormResponse;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.dtos.EventForm.EventFormDTO;
import com.oct.L3.dtos.EventFormHistoryDTO;
import com.oct.L3.entity.EventForm;
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
                .employeeId(eventForm.getEmployee().getId())
                .type(eventForm.getType())
                .date(eventForm.getDate())
                .submissionDate(eventForm.getSubmissionDate())
                .content(eventForm.getContent())
                .status(eventForm.getStatus())
                .build();
        if (eventForm.getEmployeeDataJson() != null) {
            try {
                EmployeeDTO employeeDTO = objectMapper.readValue(eventForm.getEmployeeDataJson(), EmployeeDTO.class);
                eventFormDTO.setEmployeeData(employeeDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
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
                .type(dto.getType())
                .date(dto.getDate())
                .submissionDate(dto.getSubmissionDate())
                .content(dto.getContent())
                .status(dto.getStatus())
                .employee(employeeRepository.findById(dto.getEmployeeId()).orElse(null))
                .build();

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

        if (dto.getEmployeeData() != null) {
            try {
                eventForm.setEmployeeDataJson(objectMapper.writeValueAsString(dto.getEmployeeData()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return eventForm;
    }

    public <T> EventFormResponse<T> toResponse(EventFormDTO dto, T formDetails) {
        return EventFormResponse.<T>builder()
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
                .formDetails(formDetails)
                .build();
    }
}

