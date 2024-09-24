package com.oct.L3.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDTO {
    private Integer id;
    private String name;
    private String field;
    private Date issueDate;
    private String description;

    // Getters và setters
}

