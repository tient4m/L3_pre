package com.oct.L3.convertTo;

import com.oct.L3.dtos.CertificateDTO;
import com.oct.L3.entity.Certificate;
import com.oct.L3.entity.Employee;
import com.oct.L3.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateMapper {
    private final EmployeeRepository employeeRepository;

//    public CertificateDTO toDTO(Certificate certificate) {
//        return CertificateDTO.builder()
//                .id(certificate.getId())
//                .employee(certificate.getEmployee().getId())
//                .name(certificate.getName())
//                .field(certificate.getField())
//                .issueDate(certificate.getIssueDate())
//                .description(certificate.getDescription())
//                .build();
//    }
//
//    public Certificate toEntity(CertificateDTO dto) {
//        Employee employee = null;
//        if (dto.getEmployee() != null) {
//            employee = employeeRepository.findById(dto.getEmployee()).orElse(null);
//        }
//        return Certificate.builder()
//                .Id(dto.getCertificateId())
//                .employee(employee)
//                .name(dto.getName())
//                .field(dto.getField())
//                .issueDate(dto.getIssueDate())
//                .description(dto.getDescription())
//                .build();
//    }
}
