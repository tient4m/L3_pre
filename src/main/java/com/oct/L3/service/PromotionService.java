package com.oct.L3.service;

import com.oct.L3.dtos.PromotionDTO;
import com.oct.L3.dtos.response.PromotionResponse;
import com.oct.L3.exceptions.DataNotFoundException;

public interface PromotionService {
    PromotionResponse createPromotion(PromotionDTO promotionDTO) throws DataNotFoundException;

    PromotionResponse updatePromotion(Integer id, PromotionDTO promotionDTO) throws DataNotFoundException;

    PromotionDTO getPromotionById(Integer id) throws DataNotFoundException;
}
