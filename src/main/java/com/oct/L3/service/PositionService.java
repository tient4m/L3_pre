package com.oct.L3.service;

import com.oct.L3.dtos.PositionDTO;

public interface PositionService {
    void delete(Integer positionId);

    PositionDTO save(PositionDTO positionDTO);
}
