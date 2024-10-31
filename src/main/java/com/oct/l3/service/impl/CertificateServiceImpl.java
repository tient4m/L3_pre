package com.oct.l3.service.impl;

import com.oct.l3.dtos.CertificateDTO;
import com.oct.l3.entity.CertificateEntity;
import com.oct.l3.exceptions.DataNotFoundException;
import com.oct.l3.mapper.CertificateMapper;
import com.oct.l3.repository.CertificateRepository;
import com.oct.l3.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    @Override
    public List<CertificateDTO> saveAllCertificate(List<CertificateDTO> certificateDTOList, Integer employeeId) {
        List<CertificateEntity> certificateEntities = certificateDTOList.stream()
                .map(dto -> {
                    CertificateEntity entity = certificateMapper.toEntity(dto);
                    entity.setEmployeeId(employeeId);
                    return entity;
                })
                .toList();

        return certificateRepository.saveAll(certificateEntities).stream()
                .map(certificateMapper::toDTO)
                .toList();
    }

    @Override
    public List<CertificateDTO> getAllByEmployeeId(Integer employeeId) {
        return certificateRepository.findAllByEmployeeId(employeeId).stream()
                .map(certificateMapper::toDTO)
                .toList();
    }

    @Override
    public void delete(Integer id) {
        if (certificateRepository.existsById(id)){
            throw new DataNotFoundException("Certificate not found");
        }
        certificateRepository.deleteById(id);
    }


}