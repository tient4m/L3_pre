package com.oct.L3.mapper;

import com.oct.L3.dtos.CertificateDTO;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.dtos.FamilyRelationshipDTO;
import com.oct.L3.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {


    public EmployeeDTO toDTO(EmployeeEntity employeeEntity) {
        return EmployeeDTO.builder()
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
                .build();
    }

    public EmployeeEntity toEntity(EmployeeDTO employeeDTO) {
        return EmployeeEntity.builder()
                .id(employeeDTO.getId())
                .name(employeeDTO.getName())
                .code(employeeDTO.getCode())
                .gender(employeeDTO.getGender())
                .dateOfBirth(employeeDTO.getDateOfBirth())
                .address(employeeDTO.getAddress())
                .identityCard(employeeDTO.getIdentityCard())
                .phoneNumber(employeeDTO.getPhoneNumber())
                .email(employeeDTO.getEmail())
                .positionId(employeeDTO.getPositionId())
                .managerId(employeeDTO.getManagerId())
                .status(employeeDTO.getStatus())
                .hometown(employeeDTO.getHometown())
                .ethnicity(employeeDTO.getEthnicity())
                .educationLevel(employeeDTO.getEducationLevel())
                .build();
    }

}

