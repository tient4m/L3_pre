package com.oct.l3.service;

import com.oct.l3.dtos.PositionDTO;

public interface PositionService {
    void delete(Integer positionId);

    PositionDTO save(PositionDTO positionDTO);
}
