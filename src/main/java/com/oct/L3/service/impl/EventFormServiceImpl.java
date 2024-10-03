package com.oct.L3.service.impl;

import com.oct.L3.dtos.EventFormHistoryDTO;
import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.entity.EventFormEntity;
import com.oct.L3.entity.UserEntity;
import com.oct.L3.exceptions.InvalidStatusException;
import com.oct.L3.mapper.EventFormHistoryMapper;
import com.oct.L3.dtos.eventform.EventFormDTO;
import com.oct.L3.entity.EventFormHistoryEntity;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.mapper.EventFormMapper;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.repository.EventFormHistoryRepository;
import com.oct.L3.repository.EventFormRepository;
import com.oct.L3.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.oct.L3.constant.EventType.REGISTRATION;
import static com.oct.L3.constant.EventType.TERMINATION_REQUEST;
import static com.oct.L3.constant.Status.*;


@Service
@RequiredArgsConstructor
public class EventFormServiceImpl implements EventFormService {
    private final EventFormRepository eventFormRepository;
    private final EventFormHistoryMapper eventFormHistoryMapper;
    private final EventFormHistoryRepository eventFormHistoryRepository;
    private final EmployeeRepository employeeRepository;
    private final EventFormMapper eventFormMapper;


    @Override
    public EventFormDTO createEventForm(EventFormDTO eventFormDTO) {

        if (eventFormDTO.getType().equals(TERMINATION_REQUEST)){
            if (hasIncompleteEventForms(eventFormDTO.getEmployeeId())) {
                throw new RuntimeException("EmployeeEntity has incomplete event forms");
            }
        }

        eventFormDTO.setStatus(DRAFT);
        eventFormDTO.setDate(new Date());

        return eventFormMapper.toDTO(eventFormRepository.save(eventFormMapper.toEntity(eventFormDTO)));
    }

    @Override
    public EventFormDTO updateEventForm(Integer eventFormId, EventFormDTO eventFormDTO) throws DataNotFoundException {
        EventFormEntity eventFormEntity = eventFormRepository.findById(eventFormId)
                .orElseThrow(()->
                        new DataNotFoundException("EventFormEntity not found"));
        if (
                !DRAFT.equals(eventFormEntity.getStatus()) &&
                !REJECTED.equals(eventFormEntity.getStatus()) &&
                !ADDITIONAL_REQUIREMENTS.equals(eventFormEntity.getStatus())
        ) {
            throw new RuntimeException("EventFormEntity is not in draft,rejected and additional requirements status");
        }

        if (eventFormDTO.getEmployeeId() != null) {
            eventFormEntity.setEmployeeId(eventFormDTO.getEmployeeId());
        }

        eventFormEntity.setNote(eventFormDTO.getNote());
        eventFormEntity.setContent(eventFormDTO.getContent());

        return eventFormMapper.toDTO(eventFormRepository.save(eventFormEntity));
    }


    @Override
    public EventFormDTO sendFormToLeader(Integer leaderId,
                                         Integer eventFormId,
                                         String managerComments) throws DataNotFoundException {

        EventFormEntity eventFormEntity = eventFormRepository.findById(eventFormId)
                .orElseThrow(()-> new DataNotFoundException("EventFormEntity not found"));

        if (!DRAFT.equals(eventFormEntity.getStatus()) && !ADDITIONAL_REQUIREMENTS.equals(eventFormEntity.getStatus())) {
            throw new InvalidStatusException("EventFormEntity is not in draft or additional requirements status");
        }

        eventFormEntity.setLeaderId(leaderId);
        eventFormEntity.setStatus(PENDING);
        eventFormEntity.setManagerComments(managerComments);
        eventFormEntity.setSubmissionDate(new Date());

        EventFormHistoryEntity eventFormHistoryEntity = EventFormHistoryEntity.builder()
                .eventFormId(eventFormEntity.getId())
                .status(PENDING)
                .requestDate(new Date())
                .build();

        eventFormHistoryRepository.save(eventFormHistoryEntity);

        return eventFormMapper.toDTO(eventFormRepository.save(eventFormEntity));
    }

    @Override
    public EventFormDTO getEventFormById(Integer id) throws DataNotFoundException {
        EventFormEntity eventFormEntity = eventFormRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("EventFormEntity not found"));

        return eventFormMapper.toDTO(eventFormEntity);
    }

    @Override
    public List<EventFormDTO> getAllEventFormsByManagerIdOrLeaderId(Integer id) {

        List<EventFormEntity> eventFormEntities = eventFormRepository.findAllByManagerIdOrLeaderId(id);
        return eventFormEntities.stream().map(eventFormMapper::toDTO).toList();
    }

    @Override
    public EventFormDTO updateEventFormStatus(Integer eventFormId,
                                              String leaderComments,
                                              String status) throws DataNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserEntity userEntity) {
            String userName = userEntity.getUsername();
        }



        EventFormEntity eventFormEntity = eventFormRepository.findById(eventFormId)
                .orElseThrow(()-> new DataNotFoundException("EventFormEntity not found"));

        EmployeeEntity employeeEntity = employeeRepository.findById(eventFormEntity.getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("EmployeeEntity not found"));

        if(!PENDING.equals(eventFormEntity.getStatus())){
            throw new InvalidStatusException("EventFormEntity is not in pending status");
        }

        if (leaderComments != null) {
            eventFormEntity.setLeaderComments(leaderComments);
        }

        if (eventFormEntity.getType().equals(REGISTRATION) && APPROVED.equals(status) ) {
            employeeEntity.setStatus(ACTIVE);
        }

        if (eventFormEntity.getType().equals(TERMINATION_REQUEST) && APPROVED.equals(status)){
            employeeEntity.setStatus(TERMINATED);
        }

        eventFormEntity.setStatus(status);
        eventFormEntity.setSubmissionDate(new Date());

        EventFormHistoryDTO eventFormHistoryDTO = EventFormHistoryDTO.builder()
                .eventFormId(eventFormEntity.getId())
                .status(status)
                .comments(leaderComments)
                .requestDate(new Date())
                .build();

        eventFormHistoryRepository.save(eventFormHistoryMapper.toEntity(eventFormHistoryDTO));

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

