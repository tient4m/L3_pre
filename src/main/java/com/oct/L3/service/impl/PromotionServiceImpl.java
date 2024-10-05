package com.oct.L3.service.impl;

import com.oct.L3.components.SecurityUtils;
import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.entity.PromotionEntity;
import com.oct.L3.entity.UserEntity;
import com.oct.L3.exceptions.InvalidStatusException;
import com.oct.L3.mapper.EventFormMapper;
import com.oct.L3.mapper.PromotionMapper;
import com.oct.L3.dtos.PromotionDTO;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.repository.EventFormRepository;
import com.oct.L3.repository.PositionRepository;
import com.oct.L3.repository.PromotionRepository;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.oct.L3.constant.EventType.PROMOTION;
import static com.oct.L3.constant.Status.DRAFT;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final PositionRepository positionRepository;
    private final PromotionMapper promotionMapper;
    private final EventFormService eventFormService;

    @Override
    public PromotionDTO createPromotion(PromotionDTO promotionDTO) {

        if (promotionDTO.getEventFormDTO() == null) {
            throw new RuntimeException("EventFormEntity is null");
        }
        validatePositionIds(promotionDTO.getOldPositionId(), promotionDTO.getNewPositionId());

        promotionDTO.getEventFormDTO().setType(PROMOTION);
        eventFormService.createEventForm(promotionDTO.getEventFormDTO());
        return promotionMapper.toDTO(promotionRepository.save(promotionMapper.toEntity(promotionDTO)));
    }

    @Override
    public PromotionDTO updatePromotion(Integer id, PromotionDTO promotionDTO) throws DataNotFoundException {
        validatePositionIds(promotionDTO.getOldPositionId(), promotionDTO.getNewPositionId());
        if (id.equals(promotionDTO.getId())) {
            throw new RuntimeException("Id mismatch");
        }
        eventFormService.updateEventForm(promotionDTO.getEventFormDTO().getId(), promotionDTO.getEventFormDTO());
        return promotionMapper.toDTO(promotionRepository.save(promotionMapper.toEntity(promotionDTO)));
    }

    @Override
    public PromotionDTO getPromotionById(Integer id) throws DataNotFoundException {
        PromotionEntity promotionEntity = promotionRepository.findByEventForm(id);
        if (promotionEntity == null) {
            throw new DataNotFoundException("SalaryIncreaseEntity not found");
        }
        return promotionMapper.toDTO(promotionEntity);
    }

    private void validatePositionIds(Integer oldPositionId, Integer newPositionId) throws DataNotFoundException {
        positionRepository.findById(oldPositionId)
                .orElseThrow(() -> new DataNotFoundException("OldPositionEntity not found"));
        positionRepository.findById(newPositionId)
                .orElseThrow(() -> new DataNotFoundException("NewPositionEntity not found"));
    }


}
