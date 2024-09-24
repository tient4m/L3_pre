package com.oct.L3.repository;

import com.oct.L3.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
    // Các phương thức tùy chỉnh nếu cần
}
