package com.oct.L3.service.impl;

import com.oct.L3.dtos.PositionDTO;
import com.oct.L3.entity.Position;
import com.oct.L3.repository.PositionRepository;
import com.oct.L3.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    @Override
    public void delete(Integer positionId) {
        if (!positionRepository.existsById(positionId)) {
            throw new RuntimeException("Position not found");
        }
        positionRepository.deleteById(positionId);
    }

    @Override
    public PositionDTO save(PositionDTO positionDTO) {
        Position position = Position.builder()
                .Id(positionDTO.getPositionId())
                .name(positionDTO.getName())
                .description(positionDTO.getDescription())
                .build();
        positionRepository.save(position);
        return positionDTO;
    }
}
