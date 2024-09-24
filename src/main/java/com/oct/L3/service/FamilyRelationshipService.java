package com.oct.L3.service;

import com.oct.L3.dtos.FamilyRelationshipDTO;
import com.oct.L3.entity.Employee;

import java.util.List;

public interface FamilyRelationshipService {


    void saveOrUpdateFamilyRelationships(List<FamilyRelationshipDTO> familyRelationships, Employee employee);
}
