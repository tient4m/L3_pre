package com.oct.L3.service;

import com.oct.L3.dtos.FamilyRelationshipDTO;
import com.oct.L3.entity.EmployeeEntity;

import java.util.List;

public interface FamilyRelationshipService {


    List<FamilyRelationshipDTO> saveAllFamilyRelationship(List<FamilyRelationshipDTO> familyRelationshipDTOs, Integer employeeId);

    void delete(Integer id);
}
