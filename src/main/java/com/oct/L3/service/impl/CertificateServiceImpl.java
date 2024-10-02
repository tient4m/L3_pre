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
//        List<CertificateEntity> certificateList = new ArrayList<>();
//        for (CertificateDTO certificateDTO : certificateEntities) {
//            CertificateEntity certificate;
//            if (certificateDTO.getCertificateId() != null) {
//                certificate = certificateRepository.findById(certificateDTO.getCertificateId())
//                        .orElseThrow(() -> new EntityNotFoundException("CertificateEntity not found with ID: " + certificateDTO.getCertificateId()));
//                certificate.setName(certificateDTO.getName());
//                certificate.setField(certificateDTO.getField());
//                certificate.setIssueDate(certificateDTO.getIssueDate());
//                certificate.setDescription(certificateDTO.getDescription());
//
//            } else {
//                certificate = CertificateEntity.builder()
//                        .employeeId(employeeId)
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