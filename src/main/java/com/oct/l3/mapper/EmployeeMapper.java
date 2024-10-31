package com.oct.l3.mapper;

import com.oct.l3.dtos.CertificateDTO;
import com.oct.l3.dtos.request.EmployeeRequest;
import com.oct.l3.dtos.FamilyRelationshipDTO;
import com.oct.l3.entity.*;
import com.oct.l3.repository.CertificateRepository;
import com.oct.l3.repository.FamilyRelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final CertificateRepository certificateRepository;
    private final FamilyRelationshipRepository familyRelationshipRepository;
    private final FamilyRelationshipMapper familyRelationshipMapper;
    private final CertificateMapper certificateMapper;


    public EmployeeRequest toDTO(EmployeeEntity employeeEntity) {

        List<CertificateDTO> certificateDTOS = certificateRepository.findAllByEmployeeId(employeeEntity.getId()).stream()
                .map(certificateMapper::toDTO)
                .toList();
        List<FamilyRelationshipDTO> familyRelationshipDTOS = familyRelationshipRepository.findAllByEmployeeId(employeeEntity.getId()).stream()
                .map(familyRelationshipMapper::toDTO)
                .toList();

        return EmployeeRequest.builder()
                .id(employeeEntity.getId())
                .name(employeeEntity.getName())
                .code(employeeEntity.getCode())
                .gender(employeeEntity.getGender())
                .dateOfBirth(employeeEntity.getDateOfBirth())
                .address(employeeEntity.getAddress())
                .identityCard(employeeEntity.getIdentityCard())
                .phoneNumber(employeeEntity.getPhoneNumber())
                .email(employeeEntity.getEmail())
                .positionId(employeeEntity.getPositionId())
                .managerId(employeeEntity.getManagerId())
                .status(employeeEntity.getStatus())
                .hometown(employeeEntity.getHometown())
                .ethnicity(employeeEntity.getEthnicity())
                .educationLevel(employeeEntity.getEducationLevel())
                .familyRelationships(familyRelationshipDTOS)
                .certificates(certificateDTOS)
                .build();
    }

    public EmployeeEntity toEntity(EmployeeRequest employeeRequest) {
        return EmployeeEntity.builder()
                .id(employeeRequest.getId())
                .name(employeeRequest.getName())
                .code(employeeRequest.getCode())
                .gender(employeeRequest.getGender())
                .dateOfBirth(employeeRequest.getDateOfBirth())
                .address(employeeRequest.getAddress())
                .identityCard(employeeRequest.getIdentityCard())
                .phoneNumber(employeeRequest.getPhoneNumber())
                .email(employeeRequest.getEmail())
                .positionId(employeeRequest.getPositionId())
                .managerId(employeeRequest.getManagerId())
                .status(employeeRequest.getStatus())
                .hometown(employeeRequest.getHometown())
                .ethnicity(employeeRequest.getEthnicity())
                .educationLevel(employeeRequest.getEducationLevel())
                .build();
    }

}

