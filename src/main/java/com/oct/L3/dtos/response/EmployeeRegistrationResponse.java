package com.oct.L3.dtos.response;

import com.oct.L3.dtos.EmployeeDTO;
import com.oct.L3.dtos.EventFormDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegistrationResponse {
    private EventFormDTO eventFormDTO;
    private EmployeeDTO employeeDTO;
}
