package com.oct.L3.convertTo;

import com.oct.L3.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FamilyRelationshipMapper {
    private final EmployeeRepository employeeRepository;

//    public  FamilyRelationshipDTO toDTO(FamilyRelationship familyRelationship) {
//        return FamilyRelationshipDTO.builder()
//                .relationshipId(familyRelationship.getId())
//                .employee(familyRelationship.getEmployee().getId())
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
//    public FamilyRelationship toEntity(FamilyRelationshipDTO dto) {
//        Employee employee = null;
//        if (dto.getEmployee() != null) {
//            employee = employeeRepository.findById(dto.getEmployee()).orElse(null);
//        }
//        return FamilyRelationship.builder()
//                .Id(dto.getId())
//                .employee(employee)  // Chỉ gán nếu employee không null
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

