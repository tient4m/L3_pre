package com.oct.l3.service.impl;

import com.oct.l3.dtos.EventFormDTO;
import com.oct.l3.dtos.response.PromotionResponse;
import com.oct.l3.entity.EmployeeEntity;
import com.oct.l3.entity.PromotionEntity;
import com.oct.l3.exceptions.InvalidStatusException;
import com.oct.l3.mapper.PromotionMapper;
import com.oct.l3.dtos.PromotionDTO;
import com.oct.l3.exceptions.DataNotFoundException;
import com.oct.l3.repository.EmployeeRepository;
import com.oct.l3.repository.PositionRepository;
import com.oct.l3.repository.PromotionRepository;
import com.oct.l3.service.EventFormService;
import com.oct.l3.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oct.l3.constant.EventType.PROMOTION;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final PositionRepository positionRepository;
    private final PromotionMapper promotionMapper;
    private final EventFormService eventFormService;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public PromotionResponse createPromotion(PromotionDTO promotionDTO) {

        if (promotionDTO.getEventFormDTO() == null) {
            throw new IllegalArgumentException("EventFormEntity is null");
        }
        validatePositionIds(promotionDTO);
        promotionDTO.getEventFormDTO().setType(PROMOTION);
        EventFormDTO eventFormDTO = eventFormService.createEventForm(promotionDTO.getEventFormDTO());
        promotionDTO.setEventFormDTO(eventFormDTO);
        PromotionDTO promotion = promotionMapper.toDTO(promotionRepository.save(promotionMapper.toEntity(promotionDTO)));
        return promotionMapper.toResponse(promotion);
    }

    @Override
    public PromotionResponse updatePromotion(Integer id, PromotionDTO promotionDTO) throws DataNotFoundException {
        validatePositionIds(promotionDTO);

        if (!id.equals(promotionDTO.getId())) {
            throw new IllegalArgumentException("Id mismatch");
        }
        eventFormService.updateEventForm(promotionDTO.getEventFormDTO().getId(), promotionDTO.getEventFormDTO());
        PromotionDTO promotion = promotionMapper.toDTO(promotionRepository.save(promotionMapper.toEntity(promotionDTO)));
        return promotionMapper.toResponse(promotion);
    }

    @Override
    public PromotionDTO getPromotionById(Integer id) throws DataNotFoundException {
        PromotionEntity promotionEntity = promotionRepository.findByEventForm(id);
        if (promotionEntity == null) {
            throw new DataNotFoundException("SalaryIncreaseEntity not found");
        }
        return promotionMapper.toDTO(promotionEntity);
    }

    private void validatePositionIds(PromotionDTO dto) throws DataNotFoundException {

        EmployeeEntity employeeEntity = employeeRepository.findById(dto.getEventFormDTO().getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("EmployeeEntity not found"));

        if (!employeeEntity.getPositionId().equals(dto.getOldPositionId())) {
            throw new InvalidStatusException("Old position is not correct");
        }
        if (!positionRepository.existsById(dto.getNewPositionId())) {
            throw new DataNotFoundException("NewPositionEntity not found");
        }
        if (dto.getOldPositionId().equals(dto.getNewPositionId())) {
            throw new InvalidStatusException("Old and new positions are the same");
        }
    }



}
