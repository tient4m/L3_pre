package com.oct.l3.repository;

import com.oct.l3.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity, Integer> {

    @Query(value = "SELECT * FROM certificate c WHERE c.employee_id = ?1", nativeQuery = true)
    List<CertificateEntity> findAllByEmployeeId(Integer id);
}