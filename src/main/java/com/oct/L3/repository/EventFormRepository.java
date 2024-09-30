package com.oct.L3.repository;

import com.oct.L3.entity.EventForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventFormRepository extends JpaRepository<EventForm, Integer> {
    List<EventForm> findByType(String type);

    List<EventForm> findByStatus(String status);

    @Query(value = "SELECT * FROM eventform WHERE manager_id = ?1 OR leader_id = ?1", nativeQuery = true)
    List<EventForm> findAllByManagerIdOrLeaderId(Integer id);

    List<EventForm> findByEmployeeId(Integer employeeId);
}
