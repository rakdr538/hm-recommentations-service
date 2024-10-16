package com.hm.recommendations_service.model.mappers;

import com.hm.recommendations_service.model.DAO.Product;
import com.hm.recommendations_service.model.DTO.responses.ProductResponseDto;

public class ProductMapper {
    public static ProductResponseDto mapProductsToDto(Product product) {
        return ProductResponseDto.builder()
                .description(product.getDescription())
                .productName(product.getVariation().getName())
                .inStock(product.getInStock())
                .discountedPrice(product.getDiscountedPrice())
                .originalPrice(product.getVariation().getOriginalPrice())
                .build();
    }
}
