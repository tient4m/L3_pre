package com.oct.L3.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oct.L3.entity.EventFormEntity;
import com.oct.L3.entity.UserEntity;
import com.oct.L3.mapper.EmployeeMapper;
import com.oct.L3.mapper.EventFormMapper;
import com.oct.L3.dtos.eventform.EventFormDTO;
import com.oct.L3.entity.EventFormHistoryEntity;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.repository.EventFormHistoryRepository;
import com.oct.L3.repository.EventFormRepository;
import com.oct.L3.repository.UserRepository;
import com.oct.L3.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.oct.L3.constant.EventType.REGISTRATION;
import static com.oct.L3.constant.EventType.TERMINATION_REQUEST;
import static com.oct.L3.constant.Status.*;


@Service
@RequiredArgsConstructor
public class EventFormServiceImpl implements EventFormService {
    private final EventFormRepository eventFormRepository;
    private final EventFormMapper eventFormMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepository userRepository;
    private final EventFormHistoryRepository eventFormHistoryRepository;
    private final EmployeeMapper employeeMapper;


    @Override
    public EventFormDTO saveEventForm(EventFormDTO eventFormDTO) {
        if (eventFormDTO.getType().equals(TERMINATION_REQUEST)){
            if (hasIncompleteEventForms(eventFormDTO.getEmployeeId())) {
                throw new RuntimeException("EmployeeEntity has incomplete event forms");
            }
        }
        EventFormEntity eventFormEntity = eventFormMapper.toEntity(eventFormDTO);
        eventFormRepository.save(eventFormEntity);
        return eventFormMapper.toDTO(eventFormEntity);
    }

    @Override
    public EventFormDTO updateEventForm(Integer id, EventFormDTO eventFormDTO) throws DataNotFoundException {
        EventFormEntity eventFormEntity = eventFormRepository.findById(id).orElseThrow(()-> new DataNotFoundException("EventFormEntity not found"));
        if (!DRAFT.equals(eventFormEntity.getStatus())&& !REJECTED.equals(eventFormEntity.getStatus())&&!ADDITIONAL_REQUIREMENTS.equals(eventFormEntity.getStatus())) {
            throw new DataNotFoundException("EventFormEntity is not in draft,rejected and additional requirements status");
        }
        eventFormEntity.setContent(eventFormDTO.getContent());
        eventFormEntity.setLeaderId(eventFormDTO.getLeaderId() != null ? userRepository.findById(eventFormDTO.getLeaderId()).get() : null);
        eventFormEntity.setSubmissionDate(eventFormDTO.getSubmissionDate());
        EventFormEntity savedEventFormEntity = eventFormRepository.save(eventFormEntity);
        return eventFormMapper.toDTO(savedEventFormEntity);
    }


    @Override
    public EventFormDTO sendFormToLeader(Integer leaderId, Integer eventFormId, String managerComments, Date submissionDate) throws DataNotFoundException {
        UserEntity leader = userRepository.findById(leaderId).orElseThrow(()-> new DataNotFoundException("Leader not found"));
        EventFormEntity eventFormEntity = eventFormRepository.findById(eventFormId).orElseThrow(()-> new DataNotFoundException("EventFormEntity not found"));
        eventFormEntity.setLeaderId(leader);
        eventFormEntity.setStatus(PENDING);
        eventFormEntity.setManagerComments(managerComments);
        eventFormEntity.setSubmissionDate(submissionDate);
        EventFormHistoryEntity eventFormHistoryEntity = EventFormHistoryEntity.builder()
                .eventFormId(eventFormEntity)
                .status(PENDING)
                .actionDate(submissionDate)
                .build();
        eventFormHistoryRepository.save(eventFormHistoryEntity);
        EventFormEntity savedEventFormEntity = eventFormRepository.save(eventFormEntity);
        return eventFormMapper.toDTO(savedEventFormEntity);
    }

    @Override
    public EventFormDTO getEventFormById(Integer id) throws DataNotFoundException {
        EventFormEntity eventFormEntity = eventFormRepository.findById(id).orElseThrow(()-> new DataNotFoundException("EventFormEntity not found"));
        EventFormDTO eventFormDTO = eventFormMapper.toDTO(eventFormEntity);
        return eventFormDTO;
    }

    @Override
    public List<EventFormDTO> getAllEventFormsByManagerIdOrLeaderId(Integer id) throws DataNotFoundException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()-> new DataNotFoundException("UserEntity not found"));
        List<EventFormEntity> eventFormEntities = eventFormRepository.findAllByManagerIdOrLeaderId(id);
        return eventFormEntities.stream().map(eventFormMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public EventFormDTO updateEventFormStatus(Integer eventFormId,
                                              Integer leaderId,
                                              Date  submissionDate,
                                              String leaderComments,
                                              String status) throws DataNotFoundException {
        EventFormEntity eventFormEntity = eventFormRepository.findById(eventFormId).orElseThrow(()-> new DataNotFoundException("EventFormEntity not found"));
        if(!PENDING.equals(eventFormEntity.getStatus())){
            throw new DataNotFoundException("EventFormEntity is not in pending status");
        }
        if (eventFormEntity.getLeaderId().getId() != leaderId) {
            throw new DataNotFoundException("Leader is not the leaderId of the event form");
        }
        if (leaderComments != null) {
            eventFormEntity.setLeaderComments(leaderComments);
        }
        if (eventFormEntity.getType().equals(REGISTRATION) && APPROVED.equals(status) ) {
            eventFormEntity.getEmployeeId().setStatus(ACTIVE);
        }
        if (eventFormEntity.getType().equals(TERMINATION_REQUEST) && APPROVED.equals(status)){
            eventFormEntity.getEmployeeId().setStatus(TERMINATED);
        }
        eventFormEntity.setStatus(status);
        eventFormEntity.setSubmissionDate(submissionDate);
        EventFormHistoryEntity eventFormHistoryEntity = EventFormHistoryEntity.builder()
                .eventFormId(eventFormEntity)
                .status(status)
                .comments(leaderComments)
                .actionDate(submissionDate)
                .build();
        eventFormHistoryRepository.save(eventFormHistoryEntity);
        EventFormEntity savedEventFormEntity = eventFormRepository.save(eventFormEntity);
        return eventFormMapper.toDTO(savedEventFormEntity);
    }

    private boolean hasIncompleteEventForms(Integer employeeId) {
        List<EventFormEntity> eventFormEntities = eventFormRepository.findByEmployeeId(employeeId);
        for (EventFormEntity eventFormEntity : eventFormEntities) {
            if (!isEventFormCompleted(eventFormEntity.getStatus())) {
                return true;
            }
        }
        return false;
    }

    private boolean isEventFormCompleted(String status) {
        return "APPROVED".equals(status);
    }
}

