package com.oct.L3.mapper;

import com.oct.L3.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateMapper {
    private final EmployeeRepository employeeRepository;

//    public CertificateDTO toDTO(CertificateEntity certificate) {
//        return CertificateDTO.builder()
//                .id(certificate.getId())
//                .employeeId(certificate.getEmployeeId().getId())
//                .name(certificate.getName())
//                .field(certificate.getField())
//                .issueDate(certificate.getIssueDate())
//                .description(certificate.getDescription())
//                .build();
//    }
//
//    public CertificateEntity toEntity(CertificateDTO dto) {
//        EmployeeEntity employeeId = null;
//        if (dto.getEmployeeId() != null) {
//            employeeId = employeeRepository.findById(dto.getEmployeeId()).orElse(null);
//        }
//        return CertificateEntity.builder()
//                .Id(dto.getCertificateId())
//                .employeeId(employeeId)
//                .name(dto.getName())
//                .field(dto.getField())
//                .issueDate(dto.getIssueDate())
//                .description(dto.getDescription())
//                .build();
//    }
}
