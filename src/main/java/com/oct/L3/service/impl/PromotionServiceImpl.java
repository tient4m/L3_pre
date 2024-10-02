package com.oct.L3.service.impl;

import com.oct.L3.entity.PromotionEntity;
import com.oct.L3.mapper.EventFormMapper;
import com.oct.L3.mapper.PromotionMapper;
import com.oct.L3.dtos.PromotionDTO;
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
        PromotionEntity promotionEntity = promotionMapper.toEntity(promotionDTO);
        if (!promotionEntity.getEventFormId().getEmployeeId().getStatus().equals("ACTIVE")) {
            throw new RuntimeException("EmployeeEntity is not active");
        }
        this.checkPosition(promotionDTO);
        return promotionMapper.toDTO(promotionRepository.save(promotionEntity));
    }

    @Transactional
    @Override
    public PromotionDTO updatePromotion(Integer id, PromotionDTO promotionDTO) throws DataNotFoundException {
        this.checkPosition(promotionDTO);
        eventFormService.updateEventForm(id, promotionDTO.getEventForm());
        PromotionEntity promotionEntity = PromotionEntity.builder()
                .Id(promotionDTO.getPromotionId())
                .times(promotionDTO.getTimes())
                .reason(promotionDTO.getReason())
                .oldPositionId(positionRepository.findById(promotionDTO.getOldPosition()).get())
                .newPositionId(promotionDTO.getNewPosition() == null ? null : positionRepository.findById(promotionDTO.getNewPosition()).get())
                .note(promotionDTO.getNote())
                .build();
        promotionRepository.save(promotionEntity);
//        return PromotionDTO.builder()
//                .promotionId(promotionEntity.getId())
//                .times(promotionEntity.getTimes())
//                .reason(promotionEntity.getReason())
//                .oldPositionId(promotionEntity.getOldPositionId().getId())
//                .newPositionId(promotionEntity.getNewPositionId() == null ? null : promotionEntity.getNewPositionId().getId())
//                .note(promotionEntity.getNote())
//                .eventFormId(eventFormDTO)
//                .build();
        return promotionDTO;
    }

    @Override
    public PromotionDTO getPromotionById(Integer id) throws DataNotFoundException {
        PromotionEntity promotionEntity = promotionRepository.findByEventForm(id);
        if (promotionEntity == null) {
            throw new DataNotFoundException("SalaryIncreaseEntity not found");
        }
        return promotionMapper.toDTO(promotionEntity);
    }



    private void checkPosition(PromotionDTO promotionDTO) {
        PromotionEntity promotionEntity = promotionMapper.toEntity(promotionDTO);
        if (promotionDTO.getNewPosition() != null && positionRepository.findById(promotionDTO.getNewPosition()).isEmpty()) {
            throw new RuntimeException("New positionId is not correct");
        }
        if (promotionDTO.getEventForm() == null) {
            throw new RuntimeException("EventFormEntity is required");
        }
        if (promotionDTO.getOldPosition() != null
                && promotionEntity.getEventFormId().getEmployeeId().getPositionId().equals(positionRepository.findById(promotionDTO.getOldPosition()).get())) {
            throw new RuntimeException("Old positionId is not correct");
        }
    }
}
