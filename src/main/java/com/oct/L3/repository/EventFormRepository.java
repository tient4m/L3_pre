package com.oct.L3.repository;

import com.oct.L3.entity.EventFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventFormRepository extends JpaRepository<EventFormEntity, Integer> {
    List<EventFormEntity> findByType(String type);

    List<EventFormEntity> findByStatus(String status);

    @Query(value = "SELECT * FROM event_form WHERE manager_id = ?1 OR leader_id = ?1", nativeQuery = true)
    List<EventFormEntity> findAllByManagerIdOrLeaderId(Integer id);

    List<EventFormEntity> findByEmployeeId(Integer employeeId);
}
