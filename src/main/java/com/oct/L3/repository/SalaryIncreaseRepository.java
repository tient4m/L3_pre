package com.oct.L3.repository;

import com.oct.L3.entity.SalaryIncreaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryIncreaseRepository extends JpaRepository<SalaryIncreaseEntity, Integer> {

    @Query(value = "SELECT * FROM salary_increase WHERE event_form_id = :id", nativeQuery = true)
    SalaryIncreaseEntity findByEventFormId(Integer id);

}