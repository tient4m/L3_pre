package com.oct.L3.service.impl;

import com.oct.L3.dtos.CertificateDTO;
import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.repository.CertificateRepository;
import com.oct.L3.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;

    @Override
    public void saveOrUpdateCertificates(List<CertificateDTO> certificates, EmployeeEntity employeeEntity) {

    }

}