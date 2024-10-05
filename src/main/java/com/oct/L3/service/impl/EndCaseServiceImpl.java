package com.oct.L3.service.impl;

import com.oct.L3.components.SecurityUtils;
import com.oct.L3.dtos.EndCaseDTO;
import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.entity.EndCaseEntity;
import com.oct.L3.entity.EventFormEntity;
import com.oct.L3.entity.UserEntity;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.exceptions.InvalidStatusException;
import com.oct.L3.mapper.EndCaseMapper;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.repository.EndCaseRepository;
import com.oct.L3.repository.EventFormRepository;
import com.oct.L3.service.EndCaseService;
import com.oct.L3.service.EventFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.oct.L3.constant.EventType.TERMINATION_REQUEST;
import static com.oct.L3.constant.Status.*;

@Service
@RequiredArgsConstructor
public class EndCaseServiceImpl implements EndCaseService {
    private final EndCaseRepository endCaseRepository;
    private final EmployeeRepository employeeRepository;
    private final EndCaseMapper endCaseMapper;
    private final EventFormRepository eventFormRepository;
    private final SecurityUtils securityUtils;
    private final EventFormService eventFormService;


    @Override
    @Transactional
    public EndCaseDTO createEndCase(EndCaseDTO endCaseDTO) {

        EmployeeEntity employeeEntity = employeeRepository.findById(endCaseDTO.getEventFormDTO().getEmployeeId()).orElseThrow(
                () -> new DataNotFoundException("Employee not found"));

        if (!employeeEntity.getStatus().equals(ACTIVE)) {
            throw new InvalidStatusException("EmployeeEntity is not active");
        }
        if (hasIncompleteEventForms(employeeEntity.getId())) {
            throw new RuntimeException("EmployeeEntity has incomplete event forms");
        }
        UserEntity user = securityUtils.getLoggedInUser();
        EventFormEntity eventForm = EventFormEntity.builder()
                .employeeId(endCaseDTO.getEventFormDTO().getEmployeeId())
                .managerComments(endCaseDTO.getEventFormDTO().getManagerComments())
                .type(TERMINATION_REQUEST)
                .date(new Date())
                .submissionDate(new Date())
                .content(endCaseDTO.getEventFormDTO().getContent())
                .managerId(user.getId())
                .leaderId(endCaseDTO.getEventFormDTO().getLeaderId())
                .status(PENDING)
                .note(endCaseDTO.getEventFormDTO().getNote())
                .build();
        eventFormRepository.save(eventForm);
        return endCaseMapper.toDTO(endCaseRepository.save(endCaseMapper.toEntity(endCaseDTO)));

    }

    @Override
    @Transactional
    public EndCaseDTO update(Integer id, EndCaseDTO endCaseDTO) {

        EndCaseEntity endCaseEntity = endCaseRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("EndCaseEntity not found"));

        if (!endCaseDTO.getId().equals(id)) {
            throw new RuntimeException("Id not match");
        }

        eventFormService.updateEventForm(endCaseDTO.getEventFormDTO().getId(), endCaseDTO.getEventFormDTO());

        return endCaseMapper.toDTO(endCaseRepository.save(endCaseMapper.toEntity(endCaseDTO)));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        endCaseRepository.findById(id).orElseThrow(()
                -> new DataNotFoundException("EndCaseEntity not found"));
        endCaseRepository.deleteById(id);
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
