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

    private final RecipeRepo recipeRepo;
    private final ProductService productService;
    private final OccasionRepo occasionRepo;
    private final DemographicRepo demographicRepo;
    private final CountryRepo countryRepo;
    private final CategoryRepo categoryRepo;

    @Transactional
    public UserPreferenceResponseDto getProducts(@Valid UserPreferences userPreferences) {

        // TODO using brut force, need to use criteria builder here.

        var occasions = new ArrayList<Occasion>();
        if(isString(userPreferences.getCountryCode())) {
            var c = countryRepo.findByCountryCode(userPreferences.getCountryCode());
            occasions.addAll(occasionRepo.findAll().stream().filter( o -> o.getCountries().contains(c)).toList());
        }
        if(isValidSet(userPreferences.getSeasonalStyles())) {
            occasions.addAll(occasionRepo.findAllBySeasonIn(userPreferences.getSeasonalStyles()));
        }
        if(isString(userPreferences.getEventType())) {
            occasions.addAll(occasionRepo.findAllByName(userPreferences.getEventType()));
        }

        var demographics = demographicRepo.getByGivenAgeAndGender(userPreferences.getAge(), userPreferences.getGender());
        var categories = categoryRepo.findAll().stream()
                .filter(cat -> !CollectionUtils.isEmpty(cat.getDemographics())
                        && new ArrayList<>(cat.getDemographics()).containsAll(demographics))
                .toList();

        var products = productService.getAll().stream()
                .filter(p -> new ArrayList<>(p.getOccasions()).containsAll(occasions)
                        || new ArrayList<>(p.getCategories()).containsAll(categories)).toList();

        var recipes = recipeRepo.findAllByOccasionIn(occasions);

        return UserPreferenceResponseDto.builder()
                .productResponseDtos(products.stream().map(ProductMapper::mapProductsToDto).toList())
                .recipeResponseDtos(recipes.stream().map(RecipeMapper::mapRecipeToDto).toList())
                .build();
    }

    private boolean isValidSet(Collection<String> set) {
        if(CollectionUtils.isEmpty(set)){
            return false;
        }
        return !CollectionUtils.isEmpty(set.stream().filter(this::isString).collect(Collectors.toSet()));
    }

    private boolean isString(String s) {
        return StringUtils.hasText(s);
    }
}
