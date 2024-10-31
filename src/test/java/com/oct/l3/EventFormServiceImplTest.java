package com.oct.l3;

import com.oct.l3.components.SecurityUtils;
import com.oct.l3.dtos.EventFormDTO;
import com.oct.l3.dtos.response.EventFormResponse;
import com.oct.l3.entity.*;
import com.oct.l3.exceptions.DataNotFoundException;
import com.oct.l3.exceptions.InvalidStatusException;
import com.oct.l3.mapper.*;
import com.oct.l3.repository.*;
import com.oct.l3.service.impl.EventFormServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.*;

import static com.oct.l3.constant.Status.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventFormServiceImplTest {

    @Mock
    private EventFormHistoryRepository eventFormHistoryRepository;

    @Mock
    private EventFormRepository eventFormRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventFormMapper eventFormMapper;

    @Mock
    private SecurityUtils securityUtils;

    @InjectMocks
    private EventFormServiceImpl eventFormService;



    @Test
    void testCreateEventForm_Success() {
        EventFormDTO eventFormDTO = new EventFormDTO();
        eventFormDTO.setEmployeeId(1);

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(1);
        employeeEntity.setStatus("ACTIVE");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(2);

        EventFormEntity eventFormEntity = new EventFormEntity();

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeEntity));
        when(securityUtils.getLoggedInUser()).thenReturn(userEntity);
        when(eventFormMapper.toEntity(any(EventFormDTO.class))).thenReturn(eventFormEntity);
        when(eventFormRepository.save(eventFormEntity)).thenReturn(eventFormEntity);
        when(eventFormMapper.toDTO(eventFormEntity)).thenReturn(eventFormDTO);

        EventFormDTO result = eventFormService.createEventForm(eventFormDTO);

        assertNotNull(result);
        assertEquals(DRAFT, result.getStatus());
        assertEquals(userEntity.getId(), result.getManagerId());
        verify(eventFormRepository).save(eventFormEntity);
    }

    @Test
    void testCreateEventForm_EmployeeNotFound() {
        EventFormDTO eventFormDTO = new EventFormDTO();
        eventFormDTO.setEmployeeId(1);

        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> eventFormService.createEventForm(eventFormDTO));
    }

    @Test
    void testCreateEventForm_EmployeeNotActive() {
        EventFormDTO eventFormDTO = new EventFormDTO();
        eventFormDTO.setEmployeeId(1);

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(1);
        employeeEntity.setStatus("INACTIVE");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeEntity));

        assertThrows(InvalidStatusException.class, () -> eventFormService.createEventForm(eventFormDTO));
    }

    @Test
    void testUpdateEventForm_Success() {
        Integer eventFormId = 1;
        EventFormDTO eventFormDTO = new EventFormDTO();
        eventFormDTO.setId(eventFormId);
        eventFormDTO.setNote("Updated note");
        eventFormDTO.setContent("Updated content");
        eventFormDTO.setEmployeeId(2);

        EventFormEntity eventFormEntity = new EventFormEntity();
        eventFormEntity.setId(eventFormId);
        eventFormEntity.setStatus(DRAFT);
        eventFormEntity.setManagerId(1);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        when(eventFormRepository.findById(eventFormId)).thenReturn(Optional.of(eventFormEntity));
        when(securityUtils.getLoggedInUser()).thenReturn(userEntity);
        when(eventFormRepository.save(eventFormEntity)).thenReturn(eventFormEntity);
        when(eventFormMapper.toDTO(eventFormEntity)).thenReturn(eventFormDTO);

        EventFormDTO result = eventFormService.updateEventForm(eventFormId, eventFormDTO);

        assertNotNull(result);
        verify(eventFormRepository).save(eventFormEntity);
        assertEquals("Updated note", eventFormEntity.getNote());
        assertEquals("Updated content", eventFormEntity.getContent());
        assertEquals(2, eventFormEntity.getEmployeeId());
    }

    @Test
     void testUpdateEventForm_EventFormNotFound() {
        Integer eventFormId = 1;
        EventFormDTO eventFormDTO = new EventFormDTO();
        eventFormDTO.setId(eventFormId);

        when(eventFormRepository.findById(eventFormId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> eventFormService.updateEventForm(eventFormId, eventFormDTO));
    }

    @Test
     void testUpdateEventForm_IdNotMatch() {
        Integer eventFormId = 1;
        EventFormDTO eventFormDTO = new EventFormDTO();
        eventFormDTO.setId(2);

        EventFormEntity eventFormEntity = new EventFormEntity();
        eventFormEntity.setId(eventFormId);

        when(eventFormRepository.findById(eventFormId)).thenReturn(Optional.of(eventFormEntity));

        assertThrows(RuntimeException.class, () -> eventFormService.updateEventForm(eventFormId, eventFormDTO));
    }

    @Test
     void testUpdateEventForm_InvalidStatus() {
        Integer eventFormId = 1;
        EventFormDTO eventFormDTO = new EventFormDTO();
        eventFormDTO.setId(eventFormId);

        EventFormEntity eventFormEntity = new EventFormEntity();
        eventFormEntity.setId(eventFormId);
        eventFormEntity.setStatus(APPROVED);

        when(eventFormRepository.findById(eventFormId)).thenReturn(Optional.of(eventFormEntity));

        assertThrows(RuntimeException.class, () -> eventFormService.updateEventForm(eventFormId, eventFormDTO));
    }

    @Test
     void testUpdateEventForm_ManagerNotAllowed() {
        Integer eventFormId = 1;
        EventFormDTO eventFormDTO = new EventFormDTO();
        eventFormDTO.setId(eventFormId);

        EventFormEntity eventFormEntity = new EventFormEntity();
        eventFormEntity.setId(eventFormId);
        eventFormEntity.setStatus(DRAFT);
        eventFormEntity.setManagerId(1);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(2);

        when(eventFormRepository.findById(eventFormId)).thenReturn(Optional.of(eventFormEntity));
        when(securityUtils.getLoggedInUser()).thenReturn(userEntity);

        assertThrows(AccessDeniedException.class, () -> eventFormService.updateEventForm(eventFormId, eventFormDTO));
    }

    @Test
     void testSendFormToLeader_Success() {
        Integer eventFormId = 1;
        Integer leaderId = 3;
        Date submissionDate = new Date();
        String managerComments = "Please approve";

        EventFormEntity eventFormEntity = new EventFormEntity();
        eventFormEntity.setId(eventFormId);
        eventFormEntity.setStatus(DRAFT);
        eventFormEntity.setManagerId(2);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(2);

        when(eventFormRepository.findById(eventFormId)).thenReturn(Optional.of(eventFormEntity));
        when(userRepository.existsById(leaderId)).thenReturn(true);
        when(securityUtils.getLoggedInUser()).thenReturn(userEntity);
        when(eventFormRepository.save(eventFormEntity)).thenReturn(eventFormEntity);

        EventFormDTO eventFormDTO = new EventFormDTO();
        eventFormDTO.setId(eventFormId);
        eventFormDTO.setStatus(PENDING);
        eventFormDTO.setLeaderId(leaderId);

        when(eventFormMapper.toDTO(eventFormEntity)).thenReturn(eventFormDTO);
        when(eventFormMapper.toResponse(eventFormDTO)).thenReturn(new EventFormResponse());

        EventFormResponse response = eventFormService.sendFormToLeader(leaderId, eventFormId, submissionDate, managerComments);

        assertNotNull(response);
        assertEquals(PENDING, eventFormEntity.getStatus());
        assertEquals(leaderId, eventFormEntity.getLeaderId());
        verify(eventFormHistoryRepository).save(any(EventFormHistoryEntity.class));
    }

    @Test
     void testSendFormToLeader_EventFormNotFound() {
        Integer eventFormId = 1;
        Integer leaderId = 3;

        when(eventFormRepository.findById(eventFormId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> eventFormService.sendFormToLeader(leaderId, eventFormId, new Date(), "Comments"));
    }

    @Test
     void testSendFormToLeader_InvalidStatus() {
        Integer eventFormId = 1;
        Integer leaderId = 3;

        EventFormEntity eventFormEntity = new EventFormEntity();
        eventFormEntity.setId(eventFormId);
        eventFormEntity.setStatus(APPROVED);

        when(eventFormRepository.findById(eventFormId)).thenReturn(Optional.of(eventFormEntity));

        assertThrows(InvalidStatusException.class, () -> eventFormService.sendFormToLeader(leaderId, eventFormId, new Date(), "Comments"));
    }

    @Test
     void testSendFormToLeader_LeaderNotFound() {
        Integer eventFormId = 1;
        Integer leaderId = 3;

        EventFormEntity eventFormEntity = new EventFormEntity();
        eventFormEntity.setId(eventFormId);
        eventFormEntity.setStatus(DRAFT);

        when(eventFormRepository.findById(eventFormId)).thenReturn(Optional.of(eventFormEntity));
        when(userRepository.existsById(leaderId)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> eventFormService.sendFormToLeader(leaderId, eventFormId, new Date(), "Comments"));
    }

    @Test
     void testSendFormToLeader_ManagerNotAllowed() {
        Integer eventFormId = 1;
        Integer leaderId = 3;

        EventFormEntity eventFormEntity = new EventFormEntity();
        eventFormEntity.setId(eventFormId);
        eventFormEntity.setStatus(DRAFT);
        eventFormEntity.setManagerId(2);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(4);

        when(eventFormRepository.findById(eventFormId)).thenReturn(Optional.of(eventFormEntity));
        when(userRepository.existsById(leaderId)).thenReturn(true);
        when(securityUtils.getLoggedInUser()).thenReturn(userEntity);

        assertThrows(AccessDeniedException.class, () -> eventFormService.sendFormToLeader(leaderId, eventFormId, new Date(), "Comments"));
    }
}

