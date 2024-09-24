package com.oct.L3.repository;

import com.oct.L3.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Integer> {
    // Tìm kiếm đề xuất theo event form ID
    List<Proposal> findByEventForm_Id(Integer eventFormId);
}
