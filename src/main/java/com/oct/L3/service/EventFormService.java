package com.oct.L3.service;

import com.oct.L3.dtos.EventForm.EmployeeRegistrationDTO;
import com.oct.L3.dtos.EventForm.EventFormDTO;
import com.oct.L3.entity.Employee;
import com.oct.L3.entity.EventForm;
import com.oct.L3.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EventFormService {


    EventFormDTO saveEventForm(EventFormDTO eventFormDTO);

    EventFormDTO updateEventForm(Integer id, EventFormDTO eventFormDTO) throws DataNotFoundException;

    EventFormDTO sendFormToLeader(Integer leaderId, Integer eventFormId, String content, Date submissionDate) throws DataNotFoundException;

    EventFormDTO getEventFormById(Integer id) throws DataNotFoundException;

    List<EventFormDTO> getAllEventFormsByManagerIdOrLeaderId(Integer id) throws DataNotFoundException;

    //    @Override
    //    public EventFormDTO RequestAdditionalForm(Integer eventFormId, String content, Date submissionDate, Integer leaderId) throws DataNotFoundException {
    //        EventForm eventForm = eventFormRepository.findById(eventFormId).orElseThrow(()-> new DataNotFoundException("EventForm not found"));
    //        if(!PENDING.equals(eventForm.getStatus())){
    //            throw new DataNotFoundException("EventForm is not in pending status");
    //        }
    //        return updateEventFormStatus(eventForm, leaderId, submissionDate, content, ADDITIONAL_REQUIREMENTS);
    //    }
    //
    //    @Override
    //    public EventFormDTO approveEventForm(Integer eventFormId, Integer leaderId, Date submissionDate) throws DataNotFoundException {
    //        EventForm eventForm = eventFormRepository.findById(eventFormId).orElseThrow(()-> new DataNotFoundException("EventForm not found"));
    //        if(!PENDING.equals(eventForm.getStatus())){
    //            throw new DataNotFoundException("EventForm is not in pending status");
    //        }
    //        return updateEventFormStatus(eventForm, leaderId, submissionDate, null, APPROVED);
    //    }
    //
    //    @Override
    //    public EventFormDTO rejectedEventForm(Integer eventFormId, String content, Integer leaderId, Date submissionDate) throws DataNotFoundException {
    //        EventForm eventForm = eventFormRepository.findById(eventFormId).orElseThrow(()-> new DataNotFoundException("EventForm not found"));
    //        if(!PENDING.equals(eventForm.getStatus())){
    //            throw new DataNotFoundException("EventForm is not in pending status");
    //        }
    //        return updateEventFormStatus(eventForm, leaderId, submissionDate, content, REJECTED);
    //    }
    EventFormDTO updateEventFormStatus(Integer eventFormId,
                                       Integer leaderId,
                                       Date  submissionDate,
                                       String content,
                                       String status) throws DataNotFoundException;
}
