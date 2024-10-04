package com.oct.L3.service.impl;

import com.oct.L3.components.SecurityUtils;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.mapper.EmployeeMapper;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.entity.*;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.repository.PositionRepository;
import com.oct.L3.service.CertificateService;
import com.oct.L3.service.EmployeeService;
import com.oct.L3.service.FamilyRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.oct.L3.constant.Status.DRAFT;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final CertificateService certificateService;
    private final FamilyRelationshipService familyRelationshipService;
    private final PositionRepository positionRepository;
    private final SecurityUtils securityUtils;

    @Override
    public List<EmployeeDTO> getAll() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO)  {

        if (!positionRepository.existsById(employeeDTO.getPositionId())) {
            throw new DataNotFoundException("Position not found");
        }

        UserEntity userEntity = securityUtils.getLoggedInUser();

        employeeDTO.setManagerId(userEntity.getId());
        employeeDTO.setStatus(DRAFT);
        EmployeeEntity employeeEntity =employeeRepository.save(employeeMapper.toEntity(employeeDTO));
        certificateService.saveAllCertificate(employeeDTO.getCertificates(), employeeEntity.getId());
        familyRelationshipService.saveAllFamilyRelationship(employeeDTO.getFamilyRelationships(), employeeEntity.getId());

        return employeeMapper.toDTO(employeeEntity);
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Integer id,EmployeeDTO employeeDTO) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("EmployeeEntity not found");
        }
        if (!Objects.equals(employeeDTO.getId(), id)) {
            throw new RuntimeException("Id not match");
        }
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeDTO);
        certificateService.saveAllCertificate(employeeDTO.getCertificates(), employeeEntity.getId());
        familyRelationshipService.saveAllFamilyRelationship(employeeDTO.getFamilyRelationships(), employeeEntity.getId());
        return employeeMapper.toDTO(employeeRepository.save(employeeEntity));
    }









}
