package com.oct.L3.mapper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.dtos.response.EventFormResponse;
import com.oct.L3.dtos.eventform.EventFormDTO;
import com.oct.L3.dtos.EventFormHistoryDTO;
import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.entity.EventFormEntity;
import com.oct.L3.entity.UserEntity;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.repository.EventFormHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventFormMapper {


    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EventFormHistoryMapper eventFormHistoryMapper;
    private final EventFormHistoryRepository eventFormHistoryRepository;

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

        List<EventFormHistoryDTO> histories = eventFormHistoryRepository
                .findByEventFormId(dto.getId())
                .stream()
                .map(eventFormHistoryMapper::toDTO)
                .toList();

        EmployeeEntity entity = employeeRepository.findById(dto.getEmployeeId()).get();

        return EventFormResponse.builder()
                    .eventFormId(dto.getId())
                    .employee(employeeMapper.toDTO(entity))
                    .type(dto.getType())
                    .date(dto.getDate())
                    .submissionDate(dto.getSubmissionDate())
                    .content(dto.getContent())
                    .status(dto.getStatus())
                    .note(dto.getNote())
                    .histories(histories)
                    .build();
    }
}

