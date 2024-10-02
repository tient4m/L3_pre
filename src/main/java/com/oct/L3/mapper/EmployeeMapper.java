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

    private final ModelMapper modelMapper = new ModelMapper();

    public EmployeeDTO toDTO(EmployeeEntity employeeEntity) {
        EmployeeDTO employeeDTO = modelMapper.map(employeeEntity, EmployeeDTO.class);
        if (employeeEntity.getManagerId() != null) {
            employeeDTO.setManagerId(employeeEntity.getManagerId().getId());
        }
        if (employeeEntity.getPositionId() != null) {
            employeeDTO.setPositionId(employeeEntity.getPositionId().getId());
        }
        List<CertificateDTO> certificateDTOs = employeeEntity.getCertificateEntities().stream()
                .map(certificateEntity -> modelMapper.map(certificateEntity, CertificateDTO.class))
                .collect(Collectors.toList());
        employeeDTO.setCertificates(certificateDTOs);
        List<FamilyRelationshipDTO> familyRelationshipDTOs = employeeEntity.getFamilyRelationshipEntities().stream()
                .map(familyRelationshipEntity -> modelMapper.map(familyRelationshipEntity, FamilyRelationshipDTO.class))
                .collect(Collectors.toList());
        employeeDTO.setFamilyRelationships(familyRelationshipDTOs);
        return employeeDTO;
    }

    public EmployeeEntity toEntity(EmployeeDTO dto) {
        EmployeeEntity employeeEntity = modelMapper.map(dto, EmployeeEntity.class);

        if (dto.getPositionId() != null) {
            PositionEntity positionEntity = new PositionEntity();
            positionEntity.setId(dto.getPositionId());
            employeeEntity.setPositionId(positionEntity);
        }
        if (dto.getManagerId() != null) {
            UserEntity manager = new UserEntity();
            manager.setId(dto.getManagerId());
            employeeEntity.setManagerId(manager);
        }
        if (dto.getCertificates() != null){
            employeeEntity.setCertificateEntities(dto.getCertificates().stream().
                    map(certificateDTO -> {
                        CertificateEntity certificateEntity = modelMapper.map(certificateDTO, CertificateEntity.class);
                        certificateEntity.setEmployeeId(employeeEntity);
                        return certificateEntity;
                    }).collect(Collectors.toList()));
        }
        if (dto.getFamilyRelationships() != null){
            employeeEntity.setFamilyRelationshipEntities(dto.getFamilyRelationships().stream().
                    map(familyRelationshipDTO -> {
                        FamilyRelationshipEntity familyRelationshipEntity = modelMapper.map(familyRelationshipDTO, FamilyRelationshipEntity.class);
                        familyRelationshipEntity.setEmployeeId(employeeEntity);
                        return familyRelationshipEntity;
                    }).collect(Collectors.toList()));
        }
        return employeeEntity;
    }
}

