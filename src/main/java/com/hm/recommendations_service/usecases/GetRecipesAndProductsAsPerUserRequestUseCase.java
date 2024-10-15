package com.hm.recommendations_service.usecases;

import com.hm.recommendations_service.model.DTO.requests.UserPreferences;
import com.hm.recommendations_service.model.DTO.responses.UserPreferenceResponseDto;
import com.hm.recommendations_service.service.ProductService;
import com.hm.recommendations_service.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetRecipesAndProductsAsPerUserRequestUseCase {

    private final RecipeService recipeService;
    private final ProductService productService;

    public List<UserPreferenceResponseDto> getProducts(@Valid UserPreferences userPreferences) {
        return null;
    }
}
