package com.oct.L3.repository;

import com.oct.L3.entity.EventFormHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventFormHistoryRepository extends JpaRepository<EventFormHistoryEntity, Integer> {

    List<EventFormHistoryEntity> findByEventFormId(Integer eventFormId);
}
