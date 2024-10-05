package com.oct.L3.service;

import com.oct.L3.dtos.EventFormDTO;
import com.oct.L3.dtos.response.EventFormResponse;
import com.oct.L3.exceptions.DataNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EventFormService {


    EventFormDTO createEventForm(EventFormDTO eventFormDTO);

    EventFormDTO updateEventForm(Integer id, EventFormDTO eventFormDTO) throws DataNotFoundException;

    EventFormResponse sendFormToLeader(Integer leaderId, Integer eventFormId, Date SubmissionDate, String managerComments);

    EventFormDTO getEventFormById(Integer id) throws DataNotFoundException;

    Map<String, List<Object>> getAllEventFormsByManagerIdOrLeaderId();

    EventFormResponse processEventFormByLeader(Integer eventFormId,
                                          String leaderComments,
                                          String status) throws DataNotFoundException;

    void deleteEventForm(Integer id);
}
