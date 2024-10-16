package com.hm.recommendations_service.usecases;

import com.hm.recommendations_service.model.DAO.Country;
import com.hm.recommendations_service.model.DAO.Occasion;
import com.hm.recommendations_service.model.DTO.requests.UserPreferences;
import com.hm.recommendations_service.model.DTO.responses.ProductResponseDto;
import com.hm.recommendations_service.model.DTO.responses.RecipeResponseDto;
import com.hm.recommendations_service.model.DTO.responses.UserPreferenceResponseDto;
import com.hm.recommendations_service.model.mappers.ProductMapper;
import com.hm.recommendations_service.model.mappers.RecipeMapper;
import com.hm.recommendations_service.repository.*;
import com.hm.recommendations_service.service.ProductService;
import com.hm.recommendations_service.service.RecipeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetRecipesAndProductsAsPerUserRequestUseCase {

    private final RecipeService recipeService;
    private final ProductService productService;

    @Transactional
    public UserPreferenceResponseDto getProducts(@Valid UserPreferences userPreferences) {

        var products = productService.getMatchingProducts(userPreferences.getAge(),
                userPreferences.getGender(),
                userPreferences.getSeasonalStyles(),
                userPreferences.getCountryCode(),
                userPreferences.getEventType());

        var recipes = recipeService.getMatchingRecipes(userPreferences.getEventType(),
                userPreferences.getSeasonalStyles(),
                userPreferences.getCountryCode());

        return UserPreferenceResponseDto.builder()
                .productResponseDtos(products.stream().map(ProductMapper::mapProductsToDto).toList())
                .recipeResponseDtos(recipes.stream().map(RecipeMapper::mapRecipeToDto).toList())
                .build();
    }
}
