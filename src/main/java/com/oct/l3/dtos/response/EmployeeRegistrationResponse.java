package com.oct.l3.dtos.response;

import com.oct.l3.dtos.request.EmployeeRequest;
import com.oct.l3.dtos.EventFormDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegistrationResponse {
    private EventFormDTO eventFormDTO;
    private EmployeeRequest employeeRequest;
}
