package com.oct.L3.repository;

import com.oct.L3.entity.FamilyRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRelationshipRepository extends JpaRepository<FamilyRelationship, Integer> {
    // Tìm kiếm mối quan hệ gia đình của một nhân viên cụ thể
    List<FamilyRelationship> findByEmployee_Id(Integer employeeId);

}
