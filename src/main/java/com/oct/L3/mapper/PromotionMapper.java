package com.oct.L3.mapper;

import com.oct.L3.dtos.PositionDTO;
import com.oct.L3.dtos.response.PromotionResponse;
import com.oct.L3.dtos.PromotionDTO;
import com.oct.L3.entity.EventFormEntity;
import com.oct.L3.entity.PromotionEntity;
import com.oct.L3.repository.EventFormRepository;
import com.oct.L3.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromotionMapper {

    private final EventFormMapper eventFormMapper;
    private final EventFormRepository eventFormRepository;
    private final PositionRepository positionRepository;
    private final ModelMapper modelMapper;

   public PromotionDTO toDTO(PromotionEntity promotionEntity) {

       EventFormEntity eventFormEntity = eventFormRepository.findById(promotionEntity.getEventFormId())
               .orElseThrow(() -> new RuntimeException("EventFormEntity not found"));

        return PromotionDTO.builder()
                .id(promotionEntity.getId())
                .eventFormDTO(eventFormMapper.toDTO(eventFormEntity))
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
                .eventFormId(promotionDTO.getEventFormDTO().getId())
                .times(promotionDTO.getTimes())
                .reason(promotionDTO.getReason())
                .note(promotionDTO.getNote())
                .oldPositionId(promotionDTO.getOldPositionId())
                .newPositionId(promotionDTO.getNewPositionId())
                .build();
    }

    public PromotionResponse toResponse(PromotionDTO dto) {


        PositionDTO oldPosition = modelMapper.map(positionRepository.findById(dto.getOldPositionId()).get(), PositionDTO.class);
        PositionDTO newPosition = modelMapper.map(positionRepository.findById(dto.getNewPositionId()).get(), PositionDTO.class);

        return PromotionResponse.builder()
                .promotionId(dto.getId())
                .times(dto.getTimes())
                .reason(dto.getReason())
                .note(dto.getNote())
                .newPosition(newPosition)
                .oldPosition(oldPosition)
                .eventForm(eventFormMapper.toResponse(dto.getEventFormDTO()))
                .build();
    }

    public PromotionResponse toResponse(PromotionEntity entity) {
        return toResponse(toDTO(entity));
    }
}
