package com.oct.L3.convertTo;

import com.oct.L3.Response.PromotionResponse;
import com.oct.L3.dtos.PromotionDTO;
import com.oct.L3.entity.Promotion;
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

    public PromotionDTO toDTO(Promotion entity) {
        return PromotionDTO.builder()
                .promotionId(entity.getId())
                .times(entity.getTimes())
                .reason(entity.getReason())
                .oldPosition(entity.getOldPosition().getId())
                .newPosition(entity.getNewPosition() == null ? null : entity.getNewPosition().getId())
                .note(entity.getNote())
                .eventForm(entity.getEventForm() == null ? null : EventFormMapper.toDTO(entity.getEventForm()))
                .build();
    }

    public  Promotion toEntity(PromotionDTO dto) {
        return Promotion.builder()
                .Id(dto.getPromotionId())
                .times(dto.getTimes())
                .reason(dto.getReason())
                .oldPosition(positionRepository.findById(dto.getOldPosition()).get())
                .newPosition(dto.getNewPosition() == null ? null : positionRepository.findById(dto.getNewPosition()).get())
                .note(dto.getNote())
                .eventForm(dto.getEventForm() == null ? null : EventFormMapper.toEntity(dto.getEventForm()))
                .build();
    }

    public PromotionResponse toResponse(PromotionDTO dto) {
        PromotionResponse promotionResponse = PromotionResponse.builder()
                .promotionId(dto.getPromotionId())
                .times(dto.getTimes())
                .reason(dto.getReason())
                .oldPosition(positionRepository.findById(dto.getOldPosition()).get())
                .newPosition(dto.getNewPosition() == null ? null : positionRepository.findById(dto.getNewPosition()).get())
                .note(dto.getNote())
                .build();
        return promotionResponse;
    }
}
