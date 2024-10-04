package com.oct.L3.service.impl;

import com.oct.L3.components.SecurityUtils;
import com.oct.L3.dtos.EventFormHistoryDTO;
import com.oct.L3.dtos.response.EventFormResponse;
import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.entity.EventFormEntity;
import com.oct.L3.entity.UserEntity;
import com.oct.L3.exceptions.InvalidStatusException;
import com.oct.L3.mapper.EventFormHistoryMapper;
import com.oct.L3.dtos.EventFormDTO;
import com.oct.L3.entity.EventFormHistoryEntity;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.mapper.EventFormMapper;
import com.oct.L3.repository.*;
import com.oct.L3.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.oct.L3.constant.EventType.*;
import static com.oct.L3.constant.Status.*;


@Service
@RequiredArgsConstructor
public class EventFormServiceImpl implements EventFormService {
    private final EventFormHistoryRepository eventFormHistoryRepository;
    private final EventFormRepository eventFormRepository;
    private final EmployeeRepository employeeRepository;
    private final EndCaseRepository endCaseRepository;
    private final SalaryIncreaseRepository salaryIncreaseRepository;
    private final PromotionRepository promotionRepository;
    private final ProposalRepository proposalRepository;



    private final EventFormHistoryMapper eventFormHistoryMapper;
    private final EventFormMapper eventFormMapper;
    private final SecurityUtils securityUtils;


    @Override
    public EventFormDTO createEventForm(EventFormDTO eventFormDTO) {
        UserEntity userEntity = securityUtils.getLoggedInUser();
        eventFormDTO.setManagerId(userEntity.getId());
        eventFormDTO.setStatus(DRAFT);
        eventFormDTO.setDate(new Date());
        return eventFormMapper.toDTO(eventFormRepository.save(eventFormMapper.toEntity(eventFormDTO)));
    }

    @Override
    @Transactional
    public EventFormDTO updateEventForm(Integer eventFormId, EventFormDTO eventFormDTO) throws DataNotFoundException {
        EventFormEntity eventFormEntity = eventFormRepository.findById(eventFormId)
                .orElseThrow(()->
                        new DataNotFoundException("EventFormEntity not found"));
        if (
                !DRAFT.equals(eventFormEntity.getStatus()) &&
                !REJECTED.equals(eventFormEntity.getStatus()) &&
                !ADDITIONAL_REQUIREMENTS.equals(eventFormEntity.getStatus())
        ) {
            throw new RuntimeException("EventForm is not in draft,rejected and additional requirements status");
        }

        if (eventFormDTO.getEmployeeId() != null) {
            eventFormEntity.setEmployeeId(eventFormDTO.getEmployeeId());
        }

        eventFormEntity.setNote(eventFormDTO.getNote());
        eventFormEntity.setContent(eventFormDTO.getContent());

        return eventFormMapper.toDTO(eventFormRepository.save(eventFormEntity));
    }


    @Override
    @Transactional
    public EventFormDTO sendFormToLeader(Integer leaderId,
                                         Integer eventFormId,
                                         Date setSubmissionDate,
                                         String managerComments) {

        EventFormEntity eventFormEntity = eventFormRepository.findById(eventFormId)
                .orElseThrow(()-> new DataNotFoundException("EventFormEntity not found"));

        if (!DRAFT.equals(eventFormEntity.getStatus()) && !ADDITIONAL_REQUIREMENTS.equals(eventFormEntity.getStatus())) {
            throw new InvalidStatusException("EventFormEntity is not in draft or additional requirements status");
        }

        if (!employeeRepository.existsById(leaderId)) {
            throw new DataNotFoundException("Leader not found");
        }

        UserEntity userEntity = securityUtils.getLoggedInUser();
        if (!userEntity.getId().equals(eventFormEntity.getManagerId())) {
            throw new AccessDeniedException("Manager is not allowed to send form to leader");
        }

        eventFormEntity.setLeaderId(leaderId);
        eventFormEntity.setStatus(PENDING);
        eventFormEntity.setManagerComments(managerComments);
        eventFormEntity.setSubmissionDate(setSubmissionDate);

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
    public List<EventFormResponse> getAllEventFormsByManagerIdOrLeaderId(Integer id) {
        List<EventFormEntity> eventFormEntities = eventFormRepository.findAllByManagerIdOrLeaderId(id);
        List<EventFormDTO> eventFormDTOS = eventFormEntities.stream().map(eventFormMapper::toDTO).toList();
        return eventFormDTOS.stream().map(eventFormMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public EventFormDTO processEventFormByLeader(Integer eventFormId,
                                                 String leaderComments,
                                                 String status) throws DataNotFoundException {

        EventFormEntity eventFormEntity = eventFormRepository.findById(eventFormId)
                .orElseThrow(()-> new DataNotFoundException("EventFormEntity not found"));

        if(!PENDING.equals(eventFormEntity.getStatus())){
            throw new InvalidStatusException("EventFormEntity is not in pending status");
        }

        if (leaderComments != null) {
            eventFormEntity.setLeaderComments(leaderComments);
        }
        if (status.equals(APPROVED)) {
            this.approvedEventForm(eventFormEntity);
        }
        UserEntity userEntity = securityUtils.getLoggedInUser();
        if (!userEntity.getId().equals(eventFormEntity.getLeaderId())) {
            throw new AccessDeniedException("Leader is not allowed to process event form");
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

    private void approvedEventForm(EventFormEntity eventFormEntity) {
        EmployeeEntity employeeEntity = employeeRepository.findById(eventFormEntity.getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("EmployeeEntity not found"));

        if (eventFormEntity.getType().equals(REGISTRATION)) {
            employeeEntity.setStatus(ACTIVE);
        }
        if (eventFormEntity.getType().equals(TERMINATION_REQUEST)) {
            employeeEntity.setStatus(TERMINATED);
        }
        employeeRepository.save(employeeEntity);
    }

    @Override
    @Transactional
    public void deleteEventForm(Integer id) {

        EventFormEntity eventFormEntity = eventFormRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("EventFormEntity not found"));

        UserEntity userEntity = securityUtils.getLoggedInUser();
        if (!userEntity.getId().equals(eventFormEntity.getManagerId())) {
            throw new AccessDeniedException("Manager is not allowed to delete event form");
        }
        switch (eventFormEntity.getType())
        {
            case REGISTRATION:
                proposalRepository.deleteAllByEventFormId(id);
                break;
            case SALARY_INCREASE:
                salaryIncreaseRepository.deleteAllByEventFormId(id);
                break;
            case PROMOTION:
                promotionRepository.deleteAllByEventFormId(id);
                break;
            case TERMINATION_REQUEST:
                endCaseRepository.deleteAllByEventFormId(id);
                break;
        }
        eventFormHistoryRepository.deleteAllByEventFormId(id);
        eventFormRepository.deleteById(id);
    }




}

