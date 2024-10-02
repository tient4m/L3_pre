package com.oct.L3.repository;

import com.oct.L3.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {


    // Tìm kiếm nhân viên theo trạng thái
    List<EmployeeEntity> findByStatus(String status);
    List<EmployeeEntity> findByStatusIn(List<String> status);
}
