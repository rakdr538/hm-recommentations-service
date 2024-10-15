package com.hm.recommendations_service.model.DTO.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecipeResponseDto {
    private String recipeName;
    private Float originalPrice;
    private Float discountedPrice;
    private List<ProductResponseDto> productResponseDtos;
}
