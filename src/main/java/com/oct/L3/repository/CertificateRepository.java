package com.oct.L3.repository;

import com.oct.L3.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
    // Tìm kiếm chứng chỉ của một nhân viên cụ thể
    List<Certificate> findByEmployee_Id(Integer Id);
}