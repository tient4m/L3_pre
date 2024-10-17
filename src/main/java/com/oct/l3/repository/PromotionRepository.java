package com.oct.l3.repository;

import com.oct.l3.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity, Integer> {

    @Query(value = "SELECT * FROM promotion WHERE event_form_id = :id", nativeQuery = true)
    PromotionEntity findByEventForm(Integer id);



    void deleteByEventFormId(Integer id);

    PromotionEntity findByEventFormId(Integer id);
}
