package com.oct.L3.service;

import com.oct.L3.dtos.SalaryIncreaseDTO;
import com.oct.L3.exceptions.DataNotFoundException;

public interface SalaryIncreaseService {
    SalaryIncreaseDTO createSalaryIncrease(SalaryIncreaseDTO salaryIncreaseDTO);

    SalaryIncreaseDTO updateSalaryIncrease(Integer id, SalaryIncreaseDTO salaryIncreaseDTO) throws DataNotFoundException;


    SalaryIncreaseDTO getSalaryIncreaseByEventFormId(Integer id) throws DataNotFoundException;
}
