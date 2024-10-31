package com.oct.l3.service.impl;

import com.oct.l3.components.SecurityUtils;
import com.oct.l3.dtos.EventFormDTO;
import com.oct.l3.dtos.request.EmployeeRegistrationRequest;
import com.oct.l3.dtos.response.EmployeeRegistrationResponse;
import com.oct.l3.exceptions.DataNotFoundException;
import com.oct.l3.exceptions.InvalidStatusException;
import com.oct.l3.mapper.EmployeeMapper;
import com.oct.l3.dtos.request.EmployeeRequest;
import com.oct.l3.entity.*;
import com.oct.l3.mapper.EventFormMapper;
import com.oct.l3.repository.EmployeeRepository;
import com.oct.l3.repository.EventFormRepository;
import com.oct.l3.repository.PositionRepository;
import com.oct.l3.repository.UserRepository;
import com.oct.l3.service.CertificateService;
import com.oct.l3.service.EmployeeService;
import com.oct.l3.service.FamilyRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.oct.l3.constant.EventType.REGISTRATION;
import static com.oct.l3.constant.Status.DRAFT;
import static com.oct.l3.constant.Status.PENDING;

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
    public List<EmployeeRequest> getAll() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public EmployeeRequest createEmployee(EmployeeRequest employeeRequest)  {

        if (!positionRepository.existsById(employeeRequest.getPositionId())) {
            throw new DataNotFoundException("Position not found");
        }

        UserEntity userEntity = securityUtils.getLoggedInUser();

        employeeRequest.setManagerId(userEntity.getId());
        employeeRequest.setStatus(DRAFT);
        EmployeeEntity employeeEntity = employeeRepository.save(employeeMapper.toEntity(employeeRequest));
        certificateService.saveAllCertificate(employeeRequest.getCertificates(), employeeEntity.getId());
        familyRelationshipService.saveAllFamilyRelationship(employeeRequest.getFamilyRelationships(), employeeEntity.getId());

        return employeeMapper.toDTO(employeeEntity);
    }

    @Override
    @Transactional
    public EmployeeRequest updateEmployee(Integer id, EmployeeRequest employeeRequest) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("EmployeeEntity not found");
        }
        if (!Objects.equals(employeeRequest.getId(), id)) {
            throw new IllegalArgumentException("Id not match");
        }
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeRequest);
        certificateService.saveAllCertificate(employeeRequest.getCertificates(), employeeEntity.getId());
        familyRelationshipService.saveAllFamilyRelationship(employeeRequest.getFamilyRelationships(), employeeEntity.getId());
        return employeeMapper.toDTO(employeeRepository.save(employeeEntity));
    }

    @Override
    @Transactional
    public EmployeeRegistrationResponse registrationEmployee(EmployeeRegistrationRequest request){
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
            throw new IllegalArgumentException("Employee registration request is already exist");
        }

        if (!positionRepository.existsById(request.getPositionId())){
            throw new DataNotFoundException("Position not found");
        }

        UserEntity user = securityUtils.getLoggedInUser();

        employee.setPositionId(request.getPositionId());
        employee.setStatus(PENDING);

        EventFormDTO eventForm = EventFormDTO.builder()
                .employeeId(request.getEmployeeId())
                .type(REGISTRATION)
                .date(new Date())
                .content(request.getContent())
                .status(PENDING)
                .managerComments(request.getManagerComments())
                .managerId(user.getId())
                .note(request.getNote())
                .leaderId(request.getLeaderId())
                .build();
        EventFormEntity eventFormEntity = eventFormRepository.save(eventFormMapper.toEntity(eventForm));
        return EmployeeRegistrationResponse.builder()
                .eventFormDTO(eventFormMapper.toDTO(eventFormEntity))
                .employeeRequest(employeeMapper.toDTO(employeeRepository.save(employee)))
                .build();

    }
}
