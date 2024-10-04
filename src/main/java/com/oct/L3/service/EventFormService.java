package com.oct.L3.service;

import com.oct.L3.dtos.EventFormDTO;
import com.oct.L3.exceptions.DataNotFoundException;

import java.util.Date;
import java.util.List;

public interface EventFormService {


    EventFormDTO createEventForm(EventFormDTO eventFormDTO);

    EventFormDTO updateEventForm(Integer id, EventFormDTO eventFormDTO) throws DataNotFoundException;

    EventFormDTO sendFormToLeader(Integer leaderId, Integer eventFormId, Date SubmissionDate, String managerComments);

    EventFormDTO getEventFormById(Integer id) throws DataNotFoundException;

    List<EventFormDTO> getAllEventFormsByManagerIdOrLeaderId(Integer id) throws DataNotFoundException;

    EventFormDTO updateEventFormStatus(Integer eventFormId,
                                       String leaderComments,
                                       String status) throws DataNotFoundException;
}
