package com.oct.L3.service;

import com.oct.L3.dtos.EndCaseDTO;
import com.oct.L3.dtos.response.EndCaseResponse;

public interface EndCaseService {
    EndCaseResponse createEndCase(EndCaseDTO endCaseDTO);

    EndCaseResponse update(Integer id, EndCaseDTO endCaseDTO);

    void delete(Integer id);
}
