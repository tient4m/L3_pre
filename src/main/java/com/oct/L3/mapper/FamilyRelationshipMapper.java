package com.oct.L3.mapper;

import com.oct.L3.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FamilyRelationshipMapper {
    private final EmployeeRepository employeeRepository;

//    public  FamilyRelationshipDTO toDTO(FamilyRelationshipEntity familyRelationship) {
//        return FamilyRelationshipDTO.builder()
//                .relationshipId(familyRelationship.getId())
//                .employeeId(familyRelationship.getEmployeeId().getId())
//                .fullName(familyRelationship.getFullName())
//                .gender(familyRelationship.getGender())
//                .dateOfBirth(familyRelationship.getDateOfBirth())
//                .identityCard(familyRelationship.getIdentityCard())
//                .relationship(familyRelationship.getRelationship())
//                .address(familyRelationship.getAddress())
//                .phoneNumber(familyRelationship.getPhoneNumber())
//                .email(familyRelationship.getEmail())
//                .job(familyRelationship.getJob())
//                .build();
//    }
//
//    public FamilyRelationshipEntity toEntity(FamilyRelationshipDTO dto) {
//        EmployeeEntity employeeId = null;
//        if (dto.getEmployeeId() != null) {
//            employeeId = employeeRepository.findById(dto.getEmployeeId()).orElse(null);
//        }
//        return FamilyRelationshipEntity.builder()
//                .Id(dto.getId())
//                .employeeId(employeeId)  // Chỉ gán nếu employeeId không null
//                .fullName(dto.getFullName())
//                .gender(dto.getGender())
//                .dateOfBirth(dto.getDateOfBirth())
//                .identityCard(dto.getIdentityCard())
//                .relationship(dto.getRelationship())
//                .address(dto.getAddress())
//                .phoneNumber(dto.getPhoneNumber())
//                .email(dto.getEmail())
//                .job(dto.getJob())
//                .build();
//    }

}

