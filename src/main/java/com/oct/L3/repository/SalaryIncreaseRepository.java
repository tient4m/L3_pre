package com.oct.L3.repository;

import com.oct.L3.entity.SalaryIncreaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryIncreaseRepository extends JpaRepository<SalaryIncreaseEntity, Integer> {
    // Tìm kiếm lần tăng lương theo event form ID
    List<SalaryIncreaseEntity> findByEventForm_Id(Integer eventFormId);

    @Query(value = "SELECT * FROM salaryincrease WHERE event_form_id = :id", nativeQuery = true)
    SalaryIncreaseEntity findByEventForm(Integer id);
}