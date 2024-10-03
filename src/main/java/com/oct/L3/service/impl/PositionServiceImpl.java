package com.oct.L3.service.impl;

import com.oct.L3.dtos.PositionDTO;
import com.oct.L3.entity.PositionEntity;
import com.oct.L3.repository.PositionRepository;
import com.oct.L3.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void delete(Integer positionId) {
        if (!positionRepository.existsById(positionId)) {
            throw new RuntimeException("PositionEntity not found");
        }
        positionRepository.deleteById(positionId);
    }

    @Override
    public PositionDTO save(PositionDTO positionDTO) {

        PositionEntity positionEntity = PositionEntity.builder()
                .id(positionDTO.getPositionId())
                .name(positionDTO.getName())
                .description(positionDTO.getDescription())
                .build();

        return modelMapper.map( positionRepository.save(positionEntity), PositionDTO.class);
    }


}
