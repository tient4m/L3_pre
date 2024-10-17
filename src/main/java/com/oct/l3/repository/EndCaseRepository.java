package com.oct.l3.repository;

import com.oct.l3.entity.EndCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndCaseRepository extends JpaRepository<EndCaseEntity, Integer> {


    void deleteByEventFormId(Integer id);

    EndCaseEntity findByEventFormId(Integer id);
}
