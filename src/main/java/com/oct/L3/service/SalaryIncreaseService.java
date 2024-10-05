package com.oct.L3.service;

import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.dtos.response.SalaryIncreaseResponse;

public interface SalaryIncreaseService {
    SalaryIncreaseResponse createSalaryIncrease(SalaryIncreaseDTO salaryIncreaseDTO);

    SalaryIncreaseResponse updateSalaryIncrease(Integer id, SalaryIncreaseDTO salaryIncreaseDTO) ;


}
