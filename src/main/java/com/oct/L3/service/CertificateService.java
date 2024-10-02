package com.oct.L3.service;

import com.oct.L3.dtos.CertificateDTO;
import com.oct.L3.entity.EmployeeEntity;

import java.util.List;

public interface CertificateService {

    void saveOrUpdateCertificates(List<CertificateDTO> certificates, EmployeeEntity employeeEntity);
}
