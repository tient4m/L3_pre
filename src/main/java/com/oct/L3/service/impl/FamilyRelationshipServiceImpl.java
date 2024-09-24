package com.oct.L3.service.impl;

import com.oct.L3.convertTo.FamilyRelationshipMapper;
import com.oct.L3.dtos.FamilyRelationshipDTO;
import com.oct.L3.entity.Employee;
import com.oct.L3.entity.FamilyRelationship;
import com.oct.L3.repository.FamilyRelationshipRepository;
import com.oct.L3.service.FamilyRelationshipService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyRelationshipServiceImpl implements FamilyRelationshipService {
    private final FamilyRelationshipRepository familyRelationshipRepository;
    private final FamilyRelationshipMapper familyRelationshipMapper;

    @Override
    public void saveOrUpdateFamilyRelationships(List<FamilyRelationshipDTO> familyRelationships, Employee employee) {
//        List<FamilyRelationship> relationships = new ArrayList<>();
//        for (FamilyRelationshipDTO familyDTO : familyRelationships) {
//            FamilyRelationship familyRelationship;
//            if (familyDTO.getRelationshipId() != null) {
//                familyRelationship = familyRelationshipRepository.findById(familyDTO.getRelationshipId())
//                        .orElseThrow(() -> new EntityNotFoundException("Family relationship not found with ID: " + familyDTO.getRelationshipId()));
//                familyRelationship.setFullName(familyDTO.getFullName());
//                familyRelationship.setGender(familyDTO.getGender());
//                familyRelationship.setDateOfBirth(familyDTO.getDateOfBirth());
//                familyRelationship.setIdentityCard(familyDTO.getIdentityCard());
//                familyRelationship.setRelationship(familyDTO.getRelationship());
//                familyRelationship.setAddress(familyDTO.getAddress());
//                familyRelationship.setPhoneNumber(familyDTO.getPhoneNumber());
//                familyRelationship.setEmail(familyDTO.getEmail());
//                familyRelationship.setJob(familyDTO.getJob());
//            } else {
//                familyRelationship = FamilyRelationship.builder()
//                        .employee(employee)
//                        .fullName(familyDTO.getFullName())
//                        .gender(familyDTO.getGender())
//                        .dateOfBirth(familyDTO.getDateOfBirth())
//                        .identityCard(familyDTO.getIdentityCard())
//                        .relationship(familyDTO.getRelationship())
//                        .address(familyDTO.getAddress())
//                        .phoneNumber(familyDTO.getPhoneNumber())
//                        .email(familyDTO.getEmail())
//                        .job(familyDTO.getJob())
//                        .build();
//            }
//            relationships.add(familyRelationship);
//        }
//        familyRelationshipRepository.saveAll(relationships);
 }


}
