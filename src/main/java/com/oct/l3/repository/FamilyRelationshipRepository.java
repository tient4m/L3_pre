package com.oct.l3.repository;

import com.oct.l3.entity.FamilyRelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRelationshipRepository extends JpaRepository<FamilyRelationshipEntity, Integer> {


    @Query(value = "SELECT * FROM family_relationship f WHERE f.employee_id = ?1",nativeQuery = true)
    List<FamilyRelationshipEntity> findAllByEmployeeId(Integer id);
}
