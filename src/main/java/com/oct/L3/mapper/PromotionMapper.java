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


   public PromotionDTO toDTO(PromotionEntity promotionEntity) {
        return PromotionDTO.builder()
                .id(promotionEntity.getId())
                .eventFormId(promotionEntity.getEventFormId())
                .times(promotionEntity.getTimes())
                .reason(promotionEntity.getReason())
                .note(promotionEntity.getNote())
                .oldPositionId(promotionEntity.getOldPositionId())
                .newPositionId(promotionEntity.getNewPositionId())
                .build();
    }

    public PromotionEntity toEntity(PromotionDTO promotionDTO) {
        return PromotionEntity.builder()
                .id(promotionDTO.getId())
                .eventFormId(promotionDTO.getEventFormId())
                .times(promotionDTO.getTimes())
                .reason(promotionDTO.getReason())
                .note(promotionDTO.getNote())
                .oldPositionId(promotionDTO.getOldPositionId())
                .newPositionId(promotionDTO.getNewPositionId())
                .build();
    }

    public PromotionResponse toResponse(PromotionDTO dto) {
        PromotionResponse promotionResponse = PromotionResponse.builder()
                .promotionId(dto.getPromotionId())
                .times(dto.getTimes())
                .reason(dto.getReason())
                .note(dto.getNote())
                .eventForm(dto.getEventForm())
                .build();
        return promotionResponse;
    }
}
