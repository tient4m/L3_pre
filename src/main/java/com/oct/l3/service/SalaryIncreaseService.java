package com.oct.l3.service;

import com.oct.l3.dtos.SalaryIncreaseDTO;
import com.oct.l3.dtos.response.SalaryIncreaseResponse;

public interface SalaryIncreaseService {
    SalaryIncreaseResponse createSalaryIncrease(SalaryIncreaseDTO salaryIncreaseDTO);

    SalaryIncreaseResponse updateSalaryIncrease(Integer id, SalaryIncreaseDTO salaryIncreaseDTO) ;


}
