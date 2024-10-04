package com.oct.L3.service.impl;

import com.oct.L3.components.SecurityUtils;
import com.oct.L3.dtos.EventFormDTO;
import com.oct.L3.dtos.request.EmployeeRegistrationRequest;
import com.oct.L3.dtos.response.EmployeeRegistrationRespon;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.exceptions.InvalidStatusException;
import com.oct.L3.mapper.EmployeeMapper;
import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.entity.*;
import com.oct.L3.mapper.EventFormMapper;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.repository.EventFormRepository;
import com.oct.L3.repository.PositionRepository;
import com.oct.L3.repository.UserRepository;
import com.oct.L3.service.CertificateService;
import com.oct.L3.service.EmployeeService;
import com.oct.L3.service.FamilyRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.oct.L3.constant.EventType.REGISTRATION;
import static com.oct.L3.constant.Status.DRAFT;
import static com.oct.L3.constant.Status.PENDING;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final CertificateService certificateService;
    private final FamilyRelationshipService familyRelationshipService;
    private final PositionRepository positionRepository;
    private final SecurityUtils securityUtils;
    private final EventFormRepository eventFormRepository;
    private final EventFormMapper eventFormMapper;
    private final UserRepository userRepository;

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
        EmployeeEntity employeeEntity = employeeRepository.save(employeeMapper.toEntity(employeeDTO));
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

    @Override
    @Transactional
    public EmployeeRegistrationRespon registrationEmployee(EmployeeRegistrationRequest request){
        EmployeeEntity employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow(
                ()->new DataNotFoundException("Employee not found")
        );
        if (!DRAFT.equals(employee.getStatus())){
            throw new InvalidStatusException("Employee status must be DRAFT");
        }

        if (!userRepository.existsById(request.getLeaderId())){
            throw new DataNotFoundException("Leader not found");
        }

        if(eventFormRepository.existsByEmployeeIdAndStatusAndType(request.getEmployeeId(),PENDING,REGISTRATION)){
            throw new RuntimeException("Employee registration request is already exist");
        }

        if (!positionRepository.existsById(request.getPositionId())){
            throw new DataNotFoundException("Position not found");
        }

        UserEntity user = securityUtils.getLoggedInUser();

        employee.setPositionId(request.getPositionId());
        employee.setStatus(PENDING);

        EventFormEntity eventForm = EventFormEntity.builder()
                .employeeId(request.getEmployeeId())
                .type(REGISTRATION)
                .date(new Date())
                .content(request.getContent())
                .status(PENDING)
                .managerId(user.getId())
                .note(request.getNote())
                .leaderId(request.getLeaderId())
                .build();

        EventFormDTO eventFormDTO = eventFormMapper.toDTO(eventFormRepository.save(eventForm));

        return EmployeeRegistrationRespon.builder()
                .eventFormDTO(eventFormDTO)
                .employeeDTO(employeeMapper.toDTO(employeeRepository.save(employee)))
                .build();

    }













}
