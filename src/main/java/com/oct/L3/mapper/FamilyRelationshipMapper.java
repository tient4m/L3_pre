package com.oct.L3.mapper;

import com.oct.L3.dtos.FamilyRelationshipDTO;
import com.oct.L3.entity.FamilyRelationshipEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FamilyRelationshipMapper {

    public FamilyRelationshipDTO toDTO(FamilyRelationshipEntity familyRelationshipEntity) {
        return FamilyRelationshipDTO.builder()
                .id(familyRelationshipEntity.getId())
                .employeeId(familyRelationshipEntity.getEmployeeId())
                .fullName(familyRelationshipEntity.getFullName())
                .gender(familyRelationshipEntity.getGender())
                .relationship(familyRelationshipEntity.getRelationship())
                .identityCard(familyRelationshipEntity.getIdentityCard())
                .address(familyRelationshipEntity.getAddress())
                .phoneNumber(familyRelationshipEntity.getPhoneNumber())
                .email(familyRelationshipEntity.getEmail())
                .job(familyRelationshipEntity.getJob())
                .dateOfBirth(familyRelationshipEntity.getDateOfBirth())
                .build();
    }

    public FamilyRelationshipEntity toEntity(FamilyRelationshipDTO familyRelationshipDTO) {
        return FamilyRelationshipEntity.builder()
                .id(familyRelationshipDTO.getId())
                .employeeId(familyRelationshipDTO.getEmployeeId())
                .fullName(familyRelationshipDTO.getFullName())
                .gender(familyRelationshipDTO.getGender())
                .relationship(familyRelationshipDTO.getRelationship())
                .identityCard(familyRelationshipDTO.getIdentityCard())
                .address(familyRelationshipDTO.getAddress())
                .phoneNumber(familyRelationshipDTO.getPhoneNumber())
                .email(familyRelationshipDTO.getEmail())
                .job(familyRelationshipDTO.getJob())
                .dateOfBirth(familyRelationshipDTO.getDateOfBirth())
                .build();
    }

}

