package com.oct.L3.mapper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oct.L3.dtos.response.EventFormResponse;
import com.oct.L3.dtos.eventform.EventFormDTO;
import com.oct.L3.dtos.EventFormHistoryDTO;
import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.entity.EventFormEntity;
import com.oct.L3.entity.UserEntity;
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

    public EventFormDTO toDTO(EventFormEntity eventFormEntity) {
        EventFormDTO eventFormDTO = EventFormDTO.builder()
                .Id(eventFormEntity.getId())
                .leaderComments(eventFormEntity.getLeaderComments())
                .managerComments(eventFormEntity.getManagerComments())
                .type(eventFormEntity.getType())
                .date(eventFormEntity.getDate())
                .submissionDate(eventFormEntity.getSubmissionDate())
                .content(eventFormEntity.getContent())
                .status(eventFormEntity.getStatus())
                .build();
        if (eventFormEntity.getEmployeeId() != null) {
            eventFormDTO.setEmployeeId(eventFormEntity.getEmployeeId().getId());
        }else {
            eventFormDTO.setEmployeeId(null);
        }

        if (eventFormEntity.getEmployeeId() != null) {
            eventFormDTO.setEmployeeId(eventFormEntity.getEmployeeId().getId());
        }
        if (eventFormEntity.getManagerId() != null) {
            eventFormDTO.setManagerId(eventFormEntity.getManagerId().getId());
        }
        if (eventFormEntity.getLeaderId() != null) {
            eventFormDTO.setLeaderId(eventFormEntity.getLeaderId().getId());
        }
        if (eventFormEntity.getHistories() != null) {
            eventFormDTO.setHistories(eventFormEntity.getHistories().stream()
                    .map(eventFormHistory -> modelMapper.map(eventFormHistory, EventFormHistoryDTO.class))
                    .collect(Collectors.toList()));
        }
        return eventFormDTO;
    }

    public EventFormEntity toEntity(EventFormDTO dto) {

        EventFormEntity eventFormEntity = EventFormEntity.builder()
                .Id(dto.getId())
                .leaderComments(dto.getLeaderComments())
                .managerComments(dto.getManagerComments())
                .type(dto.getType())
                .date(dto.getDate())
                .submissionDate(dto.getSubmissionDate())
                .content(dto.getContent())
                .status(dto.getStatus())
//                .employeeId(employeeRepository.findById(dto.getEmployeeId()).orElse(null))
                .build();
        if (dto.getEmployeeId() != null) {
            EmployeeEntity employeeEntity = employeeRepository.findById(dto.getEmployeeId()).orElse(null);
            eventFormEntity.setEmployeeId(employeeEntity);
        }else {
            eventFormEntity.setEmployeeId(null);
        }

        if (dto.getLeaderId() != null) {
            UserEntity leader = new UserEntity();
            leader.setId(dto.getLeaderId());
            eventFormEntity.setLeaderId(leader);
        }
        if (dto.getManagerId() != null) {
            UserEntity manager = new UserEntity();
            manager.setId(dto.getManagerId());
            eventFormEntity.setManagerId(manager);
        }
        return eventFormEntity;
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
}

