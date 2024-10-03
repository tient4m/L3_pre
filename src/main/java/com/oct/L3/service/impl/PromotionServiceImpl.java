package com.oct.L3.service.impl;

import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.entity.EventFormEntity;
import com.oct.L3.entity.PositionEntity;
import com.oct.L3.entity.PromotionEntity;
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

import static com.oct.L3.constant.Status.DRAFT;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final EventFormRepository eventFormRepository;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final EventFormMapper eventFormMapper;
    private final PromotionMapper promotionMapper;
    private final EventFormService eventFormService;

    @Override
    public PromotionDTO createPromotion(PromotionDTO promotionDTO) throws DataNotFoundException {
        EmployeeEntity employeeEntity = employeeRepository.findById(promotionDTO.getEventFormDTO().getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("EmployeeEntity not found"));

        validatePositionIds(promotionDTO.getOldPositionId(), promotionDTO.getNewPositionId());
        promotionDTO.getEventFormDTO().setType("PROMOTION");
        promotionDTO.getEventFormDTO().setStatus(DRAFT);

        if (!employeeEntity.getStatus().equals("ACTIVE")) {
            throw new RuntimeException("EmployeeEntity is not active");
        }
        if (promotionDTO.getEventFormDTO() == null) {
            throw new RuntimeException("EventFormEntity is null");
        }

        eventFormRepository.save(eventFormMapper.toEntity(promotionDTO.getEventFormDTO()));
        PromotionEntity promotionEntity = promotionMapper.toEntity(promotionDTO);
        return promotionMapper.toDTO(promotionRepository.save(promotionEntity));
    }

    @Override
    public PromotionDTO updatePromotion(Integer id, PromotionDTO promotionDTO) throws DataNotFoundException {
        validatePositionIds(promotionDTO.getOldPositionId(), promotionDTO.getNewPositionId());

        eventFormRepository.findById(promotionDTO.getEventFormDTO().getId())
                .orElseThrow(() -> new DataNotFoundException("EventFormEntity not found"));

        if (id.equals(promotionDTO.getId())) {
            throw new RuntimeException("Id mismatch");
        }

        eventFormService.updateEventForm(promotionDTO.getEventFormDTO().getId(), promotionDTO.getEventFormDTO());

        PromotionEntity promotionEntity = promotionMapper.toEntity(promotionDTO);
        promotionRepository.save(promotionEntity);
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

    private void validatePositionIds(Integer oldPositionId, Integer newPositionId) throws DataNotFoundException {
        positionRepository.findById(oldPositionId)
                .orElseThrow(() -> new DataNotFoundException("OldPositionEntity not found"));
        positionRepository.findById(newPositionId)
                .orElseThrow(() -> new DataNotFoundException("NewPositionEntity not found"));
    }


}
