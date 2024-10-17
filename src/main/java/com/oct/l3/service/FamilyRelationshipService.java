package com.oct.l3.service;

import com.oct.l3.dtos.FamilyRelationshipDTO;

import java.util.List;

public interface FamilyRelationshipService {


    List<FamilyRelationshipDTO> saveAllFamilyRelationship(List<FamilyRelationshipDTO> familyRelationshipDTOS, Integer employeeId);

    void delete(Integer id);
}
