package com.oct.L3.convertTo;

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

    public EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        if (employee.getManager() != null) {
            employeeDTO.setManagerId(employee.getManager().getId());
        }
        if (employee.getPosition() != null) {
            employeeDTO.setPositionId(employee.getPosition().getId());
        }
        List<CertificateDTO> certificateDTOs = employee.getCertificates().stream()
                .map(certificate -> modelMapper.map(certificate, CertificateDTO.class))
                .collect(Collectors.toList());
        employeeDTO.setCertificates(certificateDTOs);
        List<FamilyRelationshipDTO> familyRelationshipDTOs = employee.getFamilyRelationships().stream()
                .map(familyRelationship -> modelMapper.map(familyRelationship, FamilyRelationshipDTO.class))
                .collect(Collectors.toList());
        employeeDTO.setFamilyRelationships(familyRelationshipDTOs);
        return employeeDTO;
    }

    public Employee toEntity(EmployeeDTO dto) {
        Employee employee = modelMapper.map(dto, Employee.class);

        if (dto.getPositionId() != null) {
            Position position = new Position();
            position.setId(dto.getPositionId());
            employee.setPosition(position);
        }
        if (dto.getManagerId() != null) {
            User manager = new User();
            manager.setId(dto.getManagerId());
            employee.setManager(manager);
        }
        if (dto.getCertificates() != null){
            employee.setCertificates(dto.getCertificates().stream().
                    map(certificateDTO -> {
                        Certificate certificate = modelMapper.map(certificateDTO, Certificate.class);
                        certificate.setEmployee(employee);
                        return certificate;
                    }).collect(Collectors.toList()));
        }
        if (dto.getFamilyRelationships() != null){
            employee.setFamilyRelationships(dto.getFamilyRelationships().stream().
                    map(familyRelationshipDTO -> {
                        FamilyRelationship familyRelationship = modelMapper.map(familyRelationshipDTO, FamilyRelationship.class);
                        familyRelationship.setEmployee(employee);
                        return familyRelationship;
                    }).collect(Collectors.toList()));
        }
        return employee;
    }
}

