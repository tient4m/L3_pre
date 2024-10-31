package com.oct.l3.service.impl;

import com.oct.l3.entity.FamilyRelationshipEntity;
import com.oct.l3.exceptions.DataNotFoundException;
import com.oct.l3.mapper.FamilyRelationshipMapper;
import com.oct.l3.dtos.FamilyRelationshipDTO;
import com.oct.l3.repository.FamilyRelationshipRepository;
import com.oct.l3.service.FamilyRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyRelationshipServiceImpl implements FamilyRelationshipService {
    private final FamilyRelationshipRepository familyRelationshipRepository;
    private final FamilyRelationshipMapper familyRelationshipMapper;

    @Override
    public List<FamilyRelationshipDTO> saveAllFamilyRelationship(List<FamilyRelationshipDTO> familyRelationshipDTOS, Integer employeeId) {
        List<FamilyRelationshipEntity> familyRelationshipEntities = familyRelationshipDTOS.stream()
                .map(dto -> {
                    FamilyRelationshipEntity entity = familyRelationshipMapper.toEntity(dto);
                    entity.setEmployeeId(employeeId);
                    return entity;
                })
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

