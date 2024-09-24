package com.oct.L3.service;

import com.oct.L3.dtos.PromotionDTO;
import com.oct.L3.exceptions.DataNotFoundException;

public interface PromotionService {
    PromotionDTO createPromotion(PromotionDTO promotionDTO);

    PromotionDTO updatePromotion(Integer id, PromotionDTO promotionDTO) throws DataNotFoundException;

    PromotionDTO getPromotionById(Integer id) throws DataNotFoundException;
}
