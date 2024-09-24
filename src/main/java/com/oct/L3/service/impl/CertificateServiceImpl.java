package com.oct.L3.service.impl;

import com.oct.L3.dtos.CertificateDTO;
import com.oct.L3.entity.Certificate;
import com.oct.L3.entity.Employee;
import com.oct.L3.repository.CertificateRepository;
import com.oct.L3.service.CertificateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;

    @Override
    public void saveOrUpdateCertificates(List<CertificateDTO> certificates, Employee employee) {
//        List<Certificate> certificateList = new ArrayList<>();
//        for (CertificateDTO certificateDTO : certificates) {
//            Certificate certificate;
//            if (certificateDTO.getCertificateId() != null) {
//                certificate = certificateRepository.findById(certificateDTO.getCertificateId())
//                        .orElseThrow(() -> new EntityNotFoundException("Certificate not found with ID: " + certificateDTO.getCertificateId()));
//                certificate.setName(certificateDTO.getName());
//                certificate.setField(certificateDTO.getField());
//                certificate.setIssueDate(certificateDTO.getIssueDate());
//                certificate.setDescription(certificateDTO.getDescription());
//
//            } else {
//                certificate = Certificate.builder()
//                        .employee(employee)
//                        .name(certificateDTO.getName())
//                        .field(certificateDTO.getField())
//                        .issueDate(certificateDTO.getIssueDate())
//                        .description(certificateDTO.getDescription())
//                        .build();
//            }
//            certificateList.add(certificate);
//        }
//        certificateRepository.saveAll(certificateList);
    }

}