package com.oct.L3.repository;

import com.oct.L3.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    // Tìm kiếm nhân viên theo trạng thái
    List<Employee> findByStatus(String status);
}
