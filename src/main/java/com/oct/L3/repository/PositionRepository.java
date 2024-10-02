package com.oct.L3.repository;

import com.oct.L3.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Integer> {
    // Các phương thức tùy chỉnh nếu cần
}
