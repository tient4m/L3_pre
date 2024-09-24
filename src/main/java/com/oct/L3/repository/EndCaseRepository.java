package com.oct.L3.repository;

import com.oct.L3.entity.EndCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EndCaseRepository extends JpaRepository<EndCase, Integer> {
    // Tìm kiếm hồ sơ kết thúc theo event form I
}
