package com.oct.L3.mapper;

import com.oct.L3.dtos.EventFormHistoryDTO;
import com.oct.L3.entity.EventFormHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventFormHistoryMapper {

    public EventFormHistoryDTO toDTO(EventFormHistoryEntity eventFormHistoryEntity) {
        return EventFormHistoryDTO.builder()
                .id(eventFormHistoryEntity.getId())
                .eventFormId(eventFormHistoryEntity.getEventFormId())
                .status(eventFormHistoryEntity.getStatus())
                .requestDate(eventFormHistoryEntity.getRequestDate())
                .comments(eventFormHistoryEntity.getComments())
                .build();
    }

    public EventFormHistoryEntity toEntity(EventFormHistoryDTO eventFormHistoryDTO) {
        return EventFormHistoryEntity.builder()
                .id(eventFormHistoryDTO.getId())
                .eventFormId(eventFormHistoryDTO.getEventFormId())
                .status(eventFormHistoryDTO.getStatus())
                .requestDate(eventFormHistoryDTO.getRequestDate())
                .comments(eventFormHistoryDTO.getComments())
                .build();
    }
}
