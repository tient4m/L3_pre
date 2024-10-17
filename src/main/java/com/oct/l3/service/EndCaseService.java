package com.oct.l3.service;

import com.oct.l3.dtos.EndCaseDTO;
import com.oct.l3.dtos.response.EndCaseResponse;

public interface EndCaseService {
    EndCaseResponse createEndCase(EndCaseDTO endCaseDTO);

    EndCaseResponse update(Integer id, EndCaseDTO endCaseDTO);

    void delete(Integer id);
}
