package com.oct.L3.service.impl;

import com.oct.L3.components.SecurityUtils;
import com.oct.L3.dtos.EventFormHistoryDTO;
import com.oct.L3.dtos.response.EventFormResponse;
import com.oct.L3.dtos.response.ProposalResponse;
import com.oct.L3.entity.*;
import com.oct.L3.exceptions.InvalidStatusException;
import com.oct.L3.mapper.*;
import com.oct.L3.dtos.EventFormDTO;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.repository.*;
import com.oct.L3.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private final UserRepository userRepository;

    private final EventFormMapper eventFormMapper;
    private final EventFormHistoryMapper eventFormHistoryMapper;
    private final ProposalMapper proposalMapper;
    private final SalaryIncreaseMapper salaryIncreaseMapper;
    private final PromotionMapper promotionMapper;
    private final EndCaseMapper endCaseMapper;

    private final SecurityUtils securityUtils;



    @Override
    public EventFormDTO createEventForm(EventFormDTO eventFormDTO) {
        EmployeeEntity employeeEntity = employeeRepository.findById(eventFormDTO.getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("EmployeeEntity not found"));
        if (!employeeEntity.getStatus().equals("ACTIVE")) {
            throw new InvalidStatusException("EmployeeEntity is not active");
        }

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
        if (!eventFormId.equals(eventFormDTO.getId())) {
            throw new RuntimeException("Id not match");
        }
        if (
                !DRAFT.equals(eventFormEntity.getStatus()) &&
                !REJECTED.equals(eventFormEntity.getStatus()) &&
                !ADDITIONAL_REQUIREMENTS.equals(eventFormEntity.getStatus())
        ) {
            throw new RuntimeException("EventForm is not in draft,rejected and additional requirements status");
        }

        UserEntity userEntity = securityUtils.getLoggedInUser();
        if (!userEntity.getId().equals(eventFormEntity.getManagerId())) {
            throw new AccessDeniedException("Manager is not allowed to update event form");
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
    public EventFormResponse sendFormToLeader(Integer leaderId,
                                         Integer eventFormId,
                                         Date setSubmissionDate,
                                         String managerComments) {

        EventFormEntity eventFormEntity = eventFormRepository.findById(eventFormId)
                .orElseThrow(()-> new DataNotFoundException("EventFormEntity not found"));

        if (!DRAFT.equals(eventFormEntity.getStatus()) && !ADDITIONAL_REQUIREMENTS.equals(eventFormEntity.getStatus())) {
            throw new InvalidStatusException("EventFormEntity is not in draft or additional requirements status");
        }

        if (!userRepository.existsById(leaderId)) {
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
                .comments(managerComments)
                .build();

        eventFormHistoryRepository.save(eventFormHistoryEntity);
        EventFormDTO eventFormDTO = eventFormMapper.toDTO(eventFormRepository.save(eventFormEntity));
        return eventFormMapper.toResponse(eventFormDTO);

    }

    @Override
    public EventFormDTO getEventFormById(Integer id) throws DataNotFoundException {
        EventFormEntity eventFormEntity = eventFormRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("EventFormEntity not found"));

        return eventFormMapper.toDTO(eventFormEntity);
    }

    @Override
    public Map<String, List<Object>> getAllEventFormsByManagerIdOrLeaderId() {
        UserEntity userEntity = securityUtils.getLoggedInUser();
        List<EventFormEntity> eventFormEntities = eventFormRepository.findAllByManagerIdOrLeaderId(userEntity.getId());

        List<EventFormDTO> eventFormDTOS = eventFormEntities.stream().map(eventFormMapper::toDTO).toList();
        Map<String, List<Object>> map = new HashMap<>();
        map.putIfAbsent(REGISTRATION, new ArrayList<>());
        map.putIfAbsent(SALARY_INCREASE, new ArrayList<>());
        map.putIfAbsent(PROMOTION, new ArrayList<>());
        map.putIfAbsent(TERMINATION_REQUEST, new ArrayList<>());
        map.putIfAbsent(PROPOSAL, new ArrayList<>());

        eventFormDTOS.forEach(eventFormDTO -> {
            if (eventFormDTO.getType().equals(PROPOSAL)) {
                map.get(PROPOSAL).add(proposalMapper.toResponse(proposalRepository.findByEventFormId(eventFormDTO.getId())));
            }
            if (eventFormDTO.getType().equals(SALARY_INCREASE)) {
                map.get(SALARY_INCREASE).add(salaryIncreaseMapper.toResponse(salaryIncreaseRepository.findByEventFormId(eventFormDTO.getId())));
            }
            if (eventFormDTO.getType().equals(PROMOTION)) {
                map.get(PROMOTION).add(promotionMapper.toResponse(promotionRepository.findByEventFormId(eventFormDTO.getId())));
            }
            if (eventFormDTO.getType().equals(TERMINATION_REQUEST)) {
                map.get(TERMINATION_REQUEST).add(endCaseMapper.toResponse(endCaseRepository.findByEventFormId(eventFormDTO.getId())));
            }
            if (eventFormDTO.getType().equals(REGISTRATION)) {
                map.get(REGISTRATION).add(eventFormMapper.toResponse(eventFormDTO));
            }
        });
        return map;
    }

    @Override
    @Transactional
    public EventFormResponse processEventFormByLeader(Integer eventFormId,
                                                 String leaderComments,
                                                 String status) throws DataNotFoundException {

        EventFormEntity eventFormEntity = eventFormRepository.findById(eventFormId)
                .orElseThrow(()-> new DataNotFoundException("EventFormEntity not found"));

        if(!PENDING.equals(eventFormEntity.getStatus())){
            throw new InvalidStatusException("EventFormEntity is not in pending status");
        }
        UserEntity userEntity = securityUtils.getLoggedInUser();
        if (!userEntity.getId().equals(eventFormEntity.getLeaderId())) {
            throw new AccessDeniedException("Leader is not allowed to process event form");
        }
        if (status.equals(APPROVED)) {
            this.handleEmployeeWhenEventFormApproved(eventFormEntity);
        }
        if (leaderComments != null) {
            eventFormEntity.setLeaderComments(leaderComments);
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
        EventFormDTO eventFormDTO = eventFormMapper.toDTO(eventFormRepository.save(eventFormEntity));
        return eventFormMapper.toResponse(eventFormDTO);
    }

    @Override
    @Transactional
    public void deleteEventForm(Integer id) {

        EventFormEntity eventFormEntity = eventFormRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("EventFormEntity not found"));
        if (PENDING.equals(eventFormEntity.getStatus())) {
            throw new RuntimeException("EventFormEntity is in pending status");
        }
        UserEntity userEntity = securityUtils.getLoggedInUser();
        if (!userEntity.getId().equals(eventFormEntity.getManagerId())) {
            throw new AccessDeniedException("Manager is not allowed to delete event form");
        }
        switch (eventFormEntity.getType())
        {
            case REGISTRATION:
                proposalRepository.deleteByEventFormId(id);
                break;
            case SALARY_INCREASE:
                salaryIncreaseRepository.deleteByEventFormId(id);
                break;
            case PROMOTION:
                promotionRepository.deleteByEventFormId(id);
                break;
            case TERMINATION_REQUEST:
                endCaseRepository.deleteByEventFormId(id);
                break;
        }
        eventFormHistoryRepository.deleteAllByEventFormId(id);
        eventFormRepository.deleteById(id);
    }

    private void handleEmployeeWhenEventFormApproved(EventFormEntity eventFormEntity) {
        EmployeeEntity employeeEntity = employeeRepository.findById(eventFormEntity.getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("EmployeeEntity not found"));

        if (eventFormEntity.getType().equals(REGISTRATION)) {
            employeeEntity.setStatus(ACTIVE);
        }
        if (eventFormEntity.getType().equals(TERMINATION_REQUEST)) {
            employeeEntity.setStatus(TERMINATED);
        }
        if (PROMOTION.equals(eventFormEntity.getType())) {
            PromotionEntity promotionEntity = promotionRepository.findByEventForm(eventFormEntity.getId());
            employeeEntity.setPositionId(promotionEntity.getNewPositionId());
        }

        employeeRepository.save(employeeEntity);
    }
}

