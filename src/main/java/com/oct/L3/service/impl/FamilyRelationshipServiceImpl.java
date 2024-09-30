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

 }


}
