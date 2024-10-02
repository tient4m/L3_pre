package com.oct.L3.service.impl;

import com.oct.L3.convertTo.EventFormMapper;
import com.oct.L3.convertTo.PromotionMapper;
import com.oct.L3.dtos.PromotionDTO;
import com.oct.L3.entity.Promotion;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.repository.PositionRepository;
import com.oct.L3.repository.PromotionRepository;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;
    private final EventFormService eventFormService;
    private final EventFormMapper eventFormMapper;
    private final PositionRepository positionRepository;

    @Override
    public PromotionDTO createPromotion(PromotionDTO promotionDTO) {
        Promotion promotion = promotionMapper.toEntity(promotionDTO);
        if (!promotion.getEventForm().getEmployee().getStatus().equals("ACTIVE")) {
            throw new RuntimeException("Employee is not active");
        }
        this.checkPosition(promotionDTO);
        return promotionMapper.toDTO(promotionRepository.save(promotion));
    }

    @Transactional
    @Override
    public PromotionDTO updatePromotion(Integer id, PromotionDTO promotionDTO) throws DataNotFoundException {
        this.checkPosition(promotionDTO);
        eventFormService.updateEventForm(id, promotionDTO.getEventForm());
        Promotion promotion = Promotion.builder()
                .Id(promotionDTO.getPromotionId())
                .times(promotionDTO.getTimes())
                .reason(promotionDTO.getReason())
                .oldPosition(positionRepository.findById(promotionDTO.getOldPosition()).get())
                .newPosition(promotionDTO.getNewPosition() == null ? null : positionRepository.findById(promotionDTO.getNewPosition()).get())
                .note(promotionDTO.getNote())
                .build();
        promotionRepository.save(promotion);
//        return PromotionDTO.builder()
//                .promotionId(promotion.getId())
//                .times(promotion.getTimes())
//                .reason(promotion.getReason())
//                .oldPosition(promotion.getOldPosition().getId())
//                .newPosition(promotion.getNewPosition() == null ? null : promotion.getNewPosition().getId())
//                .note(promotion.getNote())
//                .eventForm(eventFormDTO)
//                .build();
        return promotionDTO;
    }

    @Override
    public PromotionDTO getPromotionById(Integer id) throws DataNotFoundException {
        Promotion promotion = promotionRepository.findByEventForm(id);
        if (promotion == null) {
            throw new DataNotFoundException("SalaryIncrease not found");
        }
        return promotionMapper.toDTO(promotion);
    }


    private void checkPosition(PromotionDTO promotionDTO) {
        Promotion promotion = promotionMapper.toEntity(promotionDTO);
        if (promotionDTO.getNewPosition() != null && positionRepository.findById(promotionDTO.getNewPosition()).isEmpty()) {
            throw new RuntimeException("New position is not correct");
        }
        if (promotionDTO.getEventForm() == null) {
            throw new RuntimeException("EventForm is required");
        }
        if (promotionDTO.getOldPosition() != null
                && promotion.getEventForm().getEmployee().getPosition().equals(positionRepository.findById(promotionDTO.getOldPosition()).get())) {
            throw new RuntimeException("Old position is not correct");
        }
    }
}
