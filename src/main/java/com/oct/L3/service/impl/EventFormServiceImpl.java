package com.oct.L3.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oct.L3.convertTo.EmployeeMapper;
import com.oct.L3.convertTo.EventFormMapper;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.dtos.EventForm.EventFormDTO;
import com.oct.L3.entity.Employee;
import com.oct.L3.entity.EventForm;
import com.oct.L3.entity.EventFormHistory;
import com.oct.L3.entity.User;
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
                throw new RuntimeException("Employee has incomplete event forms");
            }
        }
        EventForm eventForm = eventFormMapper.toEntity(eventFormDTO);
        eventFormRepository.save(eventForm);
        return eventFormMapper.toDTO(eventForm);
    }

    @Override
    public EventFormDTO updateEventForm(Integer id, EventFormDTO eventFormDTO) throws DataNotFoundException {
        EventForm eventForm = eventFormRepository.findById(id).orElseThrow(()-> new DataNotFoundException("EventForm not found"));
        if (!DRAFT.equals(eventForm.getStatus())&& !REJECTED.equals(eventForm.getStatus())&&!ADDITIONAL_REQUIREMENTS.equals(eventForm.getStatus())) {
            throw new DataNotFoundException("EventForm is not in draft,rejected and additional requirements status");
        }
        eventForm.setContent(eventFormDTO.getContent());
        eventForm.setLeader(eventFormDTO.getLeaderId() != null ? userRepository.findById(eventFormDTO.getLeaderId()).get() : null);
        eventForm.setSubmissionDate(eventFormDTO.getSubmissionDate());
        EventForm savedEventForm = eventFormRepository.save(eventForm);
        return eventFormMapper.toDTO(savedEventForm);
    }


    @Override
    public EventFormDTO sendFormToLeader(Integer leaderId, Integer eventFormId, String managerComments, Date submissionDate) throws DataNotFoundException {
        User leader = userRepository.findById(leaderId).orElseThrow(()-> new DataNotFoundException("Leader not found"));
        EventForm eventForm = eventFormRepository.findById(eventFormId).orElseThrow(()-> new DataNotFoundException("EventForm not found"));
        eventForm.setLeader(leader);
        eventForm.setStatus(PENDING);
        eventForm.setManagerComments(managerComments);
        eventForm.setSubmissionDate(submissionDate);
        EventFormHistory eventFormHistory = EventFormHistory.builder()
                .eventForm(eventForm)
                .status(PENDING)
                .actionDate(submissionDate)
                .build();
        eventFormHistoryRepository.save(eventFormHistory);
        EventForm savedEventForm = eventFormRepository.save(eventForm);
        return eventFormMapper.toDTO(savedEventForm);
    }

    @Override
    public EventFormDTO getEventFormById(Integer id) throws DataNotFoundException {
        EventForm eventForm = eventFormRepository.findById(id).orElseThrow(()-> new DataNotFoundException("EventForm not found"));
        EventFormDTO eventFormDTO = eventFormMapper.toDTO(eventForm);
        return eventFormDTO;
    }

    @Override
    public List<EventFormDTO> getAllEventFormsByManagerIdOrLeaderId(Integer id) throws DataNotFoundException {
        User user = userRepository.findById(id).orElseThrow(()-> new DataNotFoundException("User not found"));
        List<EventForm> eventForms = eventFormRepository.findAllByManagerIdOrLeaderId(id);
        return eventForms.stream().map(eventFormMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public EventFormDTO updateEventFormStatus(Integer eventFormId,
                                              Integer leaderId,
                                              Date  submissionDate,
                                              String leaderComments,
                                              String status) throws DataNotFoundException {
        EventForm eventForm = eventFormRepository.findById(eventFormId).orElseThrow(()-> new DataNotFoundException("EventForm not found"));
        if(!PENDING.equals(eventForm.getStatus())){
            throw new DataNotFoundException("EventForm is not in pending status");
        }
        if (eventForm.getLeader().getId() != leaderId) {
            throw new DataNotFoundException("Leader is not the leader of the event form");
        }
        if (leaderComments != null) {
            eventForm.setLeaderComments(leaderComments);
        }
        if (eventForm.getType().equals(REGISTRATION) && APPROVED.equals(status) ) {
            eventForm.getEmployee().setStatus(ACTIVE);
        }
        if (eventForm.getType().equals(TERMINATION_REQUEST) && APPROVED.equals(status)){
            eventForm.getEmployee().setStatus(TERMINATED);
        }
        eventForm.setStatus(status);
        eventForm.setSubmissionDate(submissionDate);
        EventFormHistory eventFormHistory = EventFormHistory.builder()
                .eventForm(eventForm)
                .status(status)
                .comments(leaderComments)
                .actionDate(submissionDate)
                .build();
        eventFormHistoryRepository.save(eventFormHistory);
        EventForm savedEventForm = eventFormRepository.save(eventForm);
        return eventFormMapper.toDTO(savedEventForm);
    }

    private boolean hasIncompleteEventForms(Integer employeeId) {
        List<EventForm> eventForms = eventFormRepository.findByEmployeeId(employeeId);
        for (EventForm eventForm : eventForms) {
            if (!isEventFormCompleted(eventForm.getStatus())) {
                return true;
            }
        }
        return false;
    }

    private boolean isEventFormCompleted(String status) {
        return "APPROVED".equals(status);
    }
}

