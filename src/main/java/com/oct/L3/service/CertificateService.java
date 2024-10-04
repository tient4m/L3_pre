package com.oct.L3.service;

import com.oct.L3.dtos.CertificateDTO;

import java.util.List;

public interface CertificateService {

    List<CertificateDTO> saveAllCertificate(List<CertificateDTO> certificateDTOList, Integer employeeId);

    List<CertificateDTO> getAllByEmployeeId(Integer employeeId);

    void delete(Integer id);
}
