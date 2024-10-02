package com.oct.L3.service.impl;

import com.oct.L3.convertTo.EmployeeMapper;
import com.oct.L3.convertTo.EventFormMapper;
import com.oct.L3.dtos.EventForm.EventFormDTO;
import com.oct.L3.entity.Employee;
import com.oct.L3.entity.EventForm;
import com.oct.L3.entity.EventFormHistory;
import com.oct.L3.entity.User;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.exceptions.InvalidEmployeeStatusException;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.repository.EventFormHistoryRepository;
import com.oct.L3.repository.EventFormRepository;
import com.oct.L3.repository.UserRepository;
import com.oct.L3.service.EventFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.oct.L3.constant.EventType.REGISTRATION;
import static com.oct.L3.constant.EventType.TERMINATION_REQUEST;
import static com.oct.L3.constant.Status.*;


@Service
@RequiredArgsConstructor
public class EventFormServiceImpl implements EventFormService {
    private final EventFormRepository eventFormRepository;
    private final EventFormMapper eventFormMapper;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final EventFormHistoryRepository eventFormHistoryRepository;
    private final EmployeeMapper employeeMapper;


    @Override
    public EventFormDTO saveEventForm(EventFormDTO eventFormDTO) {
        Employee employee = employeeRepository.findById(eventFormDTO.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));
        if (employee.getStatus().equals(TERMINATED) || employee.getStatus().equals(ARCHIVED)) {
            throw new InvalidEmployeeStatusException("Invalid employee status");
        }
        if (eventFormDTO.getType().equals(TERMINATION_REQUEST)) {
            if (hasIncompleteEventForms(eventFormDTO.getEmployeeId())) {
                throw new InvalidEmployeeStatusException("Employee has incomplete event forms");
            }
        }
        if (!employee.getStatus().equals(DRAFT) && eventFormDTO.getType().equals(REGISTRATION)) {
            throw new InvalidEmployeeStatusException("Invalid employee status");
        }
        EventForm eventForm = eventFormMapper.toEntity(eventFormDTO);
        eventFormRepository.save(eventForm);
        return eventFormMapper.toDTO(eventForm);
    }

    @Override
//    @Transactional
    @PostAuthorize("returnObject.managerId == principal.id")
    @PreAuthorize("hasAuthority('ROLE_MANAGER') ")
    public EventFormDTO updateEventForm(Integer id, EventFormDTO eventFormDTO) throws DataNotFoundException {
        EventForm eventForm = eventFormRepository.findById(id).orElseThrow(() -> new DataNotFoundException("EventForm not found"));
        if (!DRAFT.equals(eventForm.getStatus()) && !REJECTED.equals(eventForm.getStatus()) && !ADDITIONAL_REQUIREMENTS.equals(eventForm.getStatus())) {
            throw new DataNotFoundException("EventForm is not in draft,rejected and additional requirements status");
        }
        eventFormDTO.setId(id);
        EventForm savedEventForm = eventFormMapper.mergerEntityAndDTO(eventForm, eventFormDTO);
        return eventFormMapper.toDTO(eventFormRepository.save(savedEventForm));
    }


    @Override
    public EventFormDTO sendFormToLeader(Integer leaderId, Integer eventFormId, String managerComments, Date submissionDate) throws DataNotFoundException {
        User leader = userRepository.findById(leaderId).orElseThrow(() -> new DataNotFoundException("Leader not found"));
        EventForm eventForm = eventFormRepository.findById(eventFormId).orElseThrow(() -> new DataNotFoundException("EventForm not found"));
        eventForm.setLeader(leader);
        eventForm.getEmployee().setStatus(PENDING);
        eventForm.setStatus(PENDING);
        eventForm.setManagerComments(managerComments);
        eventForm.setSubmissionDate(submissionDate);
        EventFormHistory eventFormHistory = EventFormHistory.builder()
                .eventForm(eventForm)
                .status(PENDING)
                .actionDate(submissionDate)
                .comments(managerComments)
                .build();
        eventFormHistoryRepository.save(eventFormHistory);
        EventForm savedEventForm = eventFormRepository.save(eventForm);
        return eventFormMapper.toDTO(savedEventForm);
    }

    @Override
    @PostAuthorize("returnObject.managerId == principal.id")
    public EventFormDTO getEventFormById(Integer id) throws DataNotFoundException {
        EventForm eventForm = eventFormRepository.findById(id).orElseThrow(() -> new DataNotFoundException("EventForm not found"));
        return eventFormMapper.toDTO(eventForm);
    }

    @Override
    public List<EventFormDTO> getAllEventFormsByManagerIdOrLeaderId(Integer id) throws DataNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found"));
        List<EventForm> eventForms = eventFormRepository.findAllByManagerIdOrLeaderId(id);
        return eventForms.stream().map(eventFormMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public EventFormDTO handleFormStatusByLeader(Integer eventFormId,
                                                 Integer leaderId,
                                                 Date submissionDate,
                                                 String leaderComments,
                                                 String status) throws DataNotFoundException {
        EventForm eventForm = eventFormRepository.findById(eventFormId).orElseThrow(() -> new DataNotFoundException("EventForm not found"));
        if (!PENDING.equals(eventForm.getStatus())) {
            throw new DataNotFoundException("EventForm is not in pending status");
        }
        if (!Objects.equals(eventForm.getLeader().getId(), leaderId)) {
            throw new DataNotFoundException("Leader is not the leader of the event form");
        }
        if (leaderComments != null) {
            eventForm.setLeaderComments(leaderComments);
        }
        if (eventForm.getType().equals(REGISTRATION) && APPROVED.equals(status)) {
            eventForm.getEmployee().setStatus(ACTIVE);
        }
        if (eventForm.getType().equals(TERMINATION_REQUEST) && APPROVED.equals(status)) {
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

