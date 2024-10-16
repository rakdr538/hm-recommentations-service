package com.hm.recommendations_service.service;

import com.hm.recommendations_service.model.DAO.Occasion;
import com.hm.recommendations_service.model.DAO.Product;
import com.hm.recommendations_service.model.DTO.requests.UserPreferences;
import com.hm.recommendations_service.model.DTO.responses.ProductResponseDto;
import com.hm.recommendations_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepo productRepo;
    private final OccasionService occasionService;
    private final DemographicRepo demographicRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getMatchingProducts(Integer age, String gender, Set<String> seasonalStyles, String countryCode, String eventType) {

        var occasions = occasionService.getOccasions(countryCode, seasonalStyles, eventType);

        var demographics = demographicRepo.getByGivenAgeAndGender(age, gender);
        var categories = categoryRepo.findAll().stream()
                .filter(cat -> !CollectionUtils.isEmpty(cat.getDemographics())
                        && new ArrayList<>(cat.getDemographics()).containsAll(demographics))
                .toList();

        var products = getAll().stream()
                .filter(p -> new ArrayList<>(p.getOccasions()).containsAll(occasions)
                        || new ArrayList<>(p.getCategories()).containsAll(categories)).toList();

        return products;
    }
}
