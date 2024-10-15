package com.hm.recommendations_service.service;

import com.hm.recommendations_service.model.DTO.requests.UserPreferences;
import com.hm.recommendations_service.model.DTO.responses.ProductResponseDto;
import com.hm.recommendations_service.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepo productRepo;

    public List<ProductResponseDto> getProducts(UserPreferences userPreferences) {
        return null;
    }
}
