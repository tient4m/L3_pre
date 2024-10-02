package com.oct.L3.mapper;

import com.oct.L3.dtos.response.PromotionResponse;
import com.oct.L3.dtos.PromotionDTO;
import com.oct.L3.entity.PromotionEntity;
import com.oct.L3.repository.PositionRepository;
import com.oct.L3.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromotionMapper {
    private final PromotionRepository promotionRepository;
    private final EventFormMapper EventFormMapper;
    private final PositionRepository positionRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public PromotionDTO toDTO(PromotionEntity entity) {
        return PromotionDTO.builder()
                .promotionId(entity.getId())
                .times(entity.getTimes())
                .reason(entity.getReason())
                .oldPosition(entity.getOldPositionId().getId())
                .newPosition(entity.getNewPositionId() == null ? null : entity.getNewPositionId().getId())
                .note(entity.getNote())
                .eventForm(entity.getEventFormId() == null ? null : EventFormMapper.toDTO(entity.getEventFormId()))
                .build();
    }

    public PromotionEntity toEntity(PromotionDTO dto) {
        return PromotionEntity.builder()
                .Id(dto.getPromotionId())
                .times(dto.getTimes())
                .reason(dto.getReason())
                .oldPositionId(positionRepository.findById(dto.getOldPosition()).get())
                .newPositionId(dto.getNewPosition() == null ? null : positionRepository.findById(dto.getNewPosition()).get())
                .note(dto.getNote())
                .eventFormId(dto.getEventForm() == null ? null : EventFormMapper.toEntity(dto.getEventForm()))
                .build();
    }

    public PromotionResponse toResponse(PromotionDTO dto) {
        PromotionResponse promotionResponse = PromotionResponse.builder()
                .promotionId(dto.getPromotionId())
                .times(dto.getTimes())
                .reason(dto.getReason())
                .oldPositionEntity(positionRepository.findById(dto.getOldPosition()).get())
                .newPositionEntity(dto.getNewPosition() == null ? null : positionRepository.findById(dto.getNewPosition()).get())
                .note(dto.getNote())
                .eventForm(dto.getEventForm())
                .build();
        return promotionResponse;
    }
}
