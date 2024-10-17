package com.oct.l3.service;

import com.oct.l3.dtos.PromotionDTO;
import com.oct.l3.dtos.response.PromotionResponse;
import com.oct.l3.exceptions.DataNotFoundException;

public interface PromotionService {
    PromotionResponse createPromotion(PromotionDTO promotionDTO) throws DataNotFoundException;

    PromotionResponse updatePromotion(Integer id, PromotionDTO promotionDTO) throws DataNotFoundException;

    PromotionDTO getPromotionById(Integer id) throws DataNotFoundException;
}
