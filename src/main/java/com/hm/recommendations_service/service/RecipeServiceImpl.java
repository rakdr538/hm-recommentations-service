package com.hm.recommendations_service.service;

import com.hm.recommendations_service.model.DAO.Occasion;
import com.hm.recommendations_service.model.DAO.Product;
import com.hm.recommendations_service.model.DAO.Recipe;
import com.hm.recommendations_service.repository.RecipeRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepo recipeRepo;
    private final OccasionService occasionService;

    @Transactional
    @Override
    public Recipe create(@NotNull @NotEmpty String recipeName,
                         @NotNull List<Product> products,
                         Occasion occasion) {
        var totalRecipePrice = (float) products.stream()
                .mapToDouble(p-> p.getInStock() ? p.getDiscountedPrice() : 0)
                .sum();

        var inStockProducts = products.stream().filter(Product::getInStock).toList();

        return recipeRepo.save(Recipe.builder()
                .name(recipeName)
                .products(inStockProducts)
                .recipeOriginalPrice(totalRecipePrice)
                .recipeDiscountedPrice(totalRecipePrice)
                .occasion(occasion)
                .build());
    }

    @Override
    public List<Recipe> getAll() {
        return recipeRepo.findAll();
    }

    @Override
    public List<Recipe> getMatchingRecipes(String eventType, Set<String> seasonalStyles, String countryCode) {
        var occasions = occasionService.getOccasions(countryCode, seasonalStyles, eventType);
        return recipeRepo.findAllByOccasionIn(occasions);
    }
}
