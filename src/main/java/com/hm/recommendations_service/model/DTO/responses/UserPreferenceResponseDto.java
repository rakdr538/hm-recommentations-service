package com.hm.recommendations_service.model.DTO.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserPreferenceResponseDto {
    List<ProductResponseDto> productResponseDtos;
    List<RecipeResponseDto> recipeResponseDtos;
}
