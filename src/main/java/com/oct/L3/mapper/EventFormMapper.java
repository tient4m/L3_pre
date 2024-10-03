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


    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EventFormDTO toDTO(EventFormEntity entity) {
        return EventFormDTO.builder()
                .id(entity.getId())
                .employeeId(entity.getEmployeeId())
                .managerComments(entity.getManagerComments())
                .leaderComments(entity.getLeaderComments())
                .type(entity.getType())
                .date(entity.getDate())
                .submissionDate(entity.getSubmissionDate())
                .content(entity.getContent())
                .status(entity.getStatus())
                .note(entity.getNote())
                .leaderId(entity.getLeaderId())
                .managerId(entity.getManagerId())
                .build();
    }

    public EventFormEntity toEntity(EventFormDTO dto) {
        return EventFormEntity.builder()
                .id(dto.getId())
                .employeeId(dto.getEmployeeId())
                .managerComments(dto.getManagerComments())
                .leaderComments(dto.getLeaderComments())
                .type(dto.getType())
                .date(dto.getDate())
                .submissionDate(dto.getSubmissionDate())
                .content(dto.getContent())
                .status(dto.getStatus())
                .note(dto.getNote())
                .leaderId(dto.getLeaderId())
                .managerId(dto.getManagerId())
                .build();
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

