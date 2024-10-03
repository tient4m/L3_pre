package com.oct.L3.repository;

import com.oct.L3.entity.EndCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndCaseRepository extends JpaRepository<EndCaseEntity, Integer> {
}
