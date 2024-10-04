package com.oct.L3.service;

import com.oct.L3.dtos.EndCaseDTO;

public interface EndCaseService {
    EndCaseDTO createEndCase(EndCaseDTO endCaseDTO);

    EndCaseDTO update(Integer id, EndCaseDTO endCaseDTO);

    void delete(Integer id);
}
