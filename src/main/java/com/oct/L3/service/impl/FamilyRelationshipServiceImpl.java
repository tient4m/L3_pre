package com.oct.L3.service.impl;

import com.oct.L3.entity.FamilyRelationshipEntity;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.mapper.FamilyRelationshipMapper;
import com.oct.L3.dtos.FamilyRelationshipDTO;
import com.oct.L3.repository.FamilyRelationshipRepository;
import com.oct.L3.service.FamilyRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyRelationshipServiceImpl implements FamilyRelationshipService {
    private final FamilyRelationshipRepository familyRelationshipRepository;
    private final FamilyRelationshipMapper familyRelationshipMapper;

    @Override
    public List<FamilyRelationshipDTO> saveAllFamilyRelationship(List<FamilyRelationshipDTO> familyRelationshipDTOs, Integer employeeId) {
        List<FamilyRelationshipEntity> familyRelationshipEntities = familyRelationshipDTOs.stream()
                .map(familyRelationshipMapper::toEntity)
                .peek(employeeEntity -> employeeEntity.setEmployeeId(employeeId))
                .toList();
        return familyRelationshipRepository.saveAll(familyRelationshipEntities).stream()
                .map(familyRelationshipMapper::toDTO)
                .toList();
    }

    @Override
    public void delete(Integer id) {
        if (familyRelationshipRepository.existsById(id)){
            throw new DataNotFoundException("Family Relationship not found");
        }
        familyRelationshipRepository.deleteById(id);
    }

 }

