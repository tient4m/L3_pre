package com.oct.l3.repository;

import com.oct.l3.entity.ProposalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository<ProposalEntity, Integer> {

    @Query(value = "SELECT * FROM proposal WHERE event_form_id = :id", nativeQuery = true)
    ProposalEntity findByEventForm(Integer id);

    void deleteByEventFormId(Integer id);

    ProposalEntity findByEventFormId(Integer id);
}
