package com.oct.L3.mapper;

import com.oct.L3.dtos.CertificateDTO;
import com.oct.L3.entity.CertificateEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateMapper {

    public CertificateDTO toDTO(CertificateEntity certificateEntity) {
        return CertificateDTO.builder()
                .id(certificateEntity.getId())
                .name(certificateEntity.getName())
                .issueDate(certificateEntity.getIssueDate())
                .build();
    }

    public CertificateEntity toEntity(CertificateDTO certificateDTO) {
        return CertificateEntity.builder()
                .id(certificateDTO.getId())
                .name(certificateDTO.getName())
                .issueDate(certificateDTO.getIssueDate())
                .build();
    }


}
