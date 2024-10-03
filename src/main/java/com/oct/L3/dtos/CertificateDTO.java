package com.oct.L3.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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

    private Integer employeeId;

    @NotBlank(message = "CertificateEntity name is required")
    private String name;

    @NotBlank(message = "Field of certificate is required")
    private String field;

    @PastOrPresent(message = "Issue date must be in the past or today")
    private Date issueDate;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}

