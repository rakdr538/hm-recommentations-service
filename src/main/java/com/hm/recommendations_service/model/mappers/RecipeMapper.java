package com.hm.recommendations_service.model.mappers;

import com.hm.recommendations_service.model.DAO.Recipe;
import com.hm.recommendations_service.model.DTO.responses.ProductResponseDto;
import com.hm.recommendations_service.model.DTO.responses.RecipeResponseDto;

public class RecipeMapper {
    public static RecipeResponseDto mapRecipeToDto(Recipe recipe) {
        return RecipeResponseDto.builder()
                .recipeName(recipe.getName())
                .originalPrice(recipe.getRecipeOriginalPrice())
                .discountedPrice(recipe.getRecipeDiscountedPrice())
                .productResponseDtos(recipe.getProducts().stream().map(ProductMapper::mapProductsToDto).toList())
                .build();
    }
}
