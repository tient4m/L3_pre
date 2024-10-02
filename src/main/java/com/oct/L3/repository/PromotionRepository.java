package com.oct.L3.repository;

import com.oct.L3.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity, Integer> {
    // Tìm kiếm lần thăng chức theo event form ID
    List<PromotionEntity> findByEventForm_Id(Integer eventFormId);
    @Query(value = "SELECT * FROM promotion WHERE event_form_id = :id", nativeQuery = true)
    PromotionEntity findByEventForm(Integer id);
}
