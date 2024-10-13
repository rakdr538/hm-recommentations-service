package com.hm.recommendations_service;

import com.hm.recommendations_service.model.DAO.Category;
import com.hm.recommendations_service.model.DAO.Country;
import com.hm.recommendations_service.model.DAO.Demographic;
import com.hm.recommendations_service.model.DAO.Occasion;
import com.hm.recommendations_service.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class RecommendationsServiceApplicationTests {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    CountryRepo countryRepo;

    @Autowired
    DemographicRepo demographicRepo;

    @Autowired
    OccasionRepo occasionRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    RecipeRepo recipeRepo;

    @Autowired
    VariationRepo variationRepo;

    @Test
    void contextLoads() {
    }

    @Test
    void createTestData() {
        var countries = countryRepo.findAll();
        Assertions.assertEquals(countries.size(), 3);

        occasionRepo.saveAll(getAllOccasions(countries));
		var occasions = occasionRepo.findAll();
		Assertions.assertEquals(occasions.size(), 3);

		var demographics = demographicRepo.findAll();
		Assertions.assertEquals(demographics.size(), 6);

		categoryRepo.saveAll(getAllCategories(demographics));
		var categories = categoryRepo.findAll();
		Assertions.assertEquals(categories.size(), 4);

    }

	private List<Category> getAllCategories(List<Demographic> demographics) {
		var categories = new ArrayList<Category>();
		var kids = demographics.stream().filter(d -> d.getToAge() <= 18).toList();
		categories.add(Category.builder()
				.name("Kids Clothes")
				.description("For kids")
				.image_url("some_image")
				.thumbnail_url("some_thumbnail_url")
				.demographic(kids)
				.build());

		categories.add(Category.builder()
				.name("Kids Shoes")
				.description("For kids shoes")
				.image_url("some_image")
				.thumbnail_url("some_thumbnail_url")
				.demographic(kids)
				.build());

		categories.add(Category.builder()
				.name("Kids Accessories")
				.description("For kids accessories")
				.image_url("some_image")
				.thumbnail_url("some_thumbnail_url")
				.demographic(kids)
				.build());

		var adults = demographics.stream().filter(d -> d.getGender().equals("all")).toList();
		categories.add(Category.builder()
				.name("Adults Clothes")
				.description("For grown-ups")
				.image_url("some_image_url_adults")
				.thumbnail_url("some_thumbnail_url_for_adults")
				.demographic(adults)
				.build());

		return categories;
	}

	private List<Occasion> getAllOccasions(List<Country> countries) {
		var occasions = new ArrayList<Occasion>();
		occasions.add(Occasion.builder()
				.name("Christmas")
				.occasionStart(Timestamp.valueOf(LocalDateTime.of(2024, 12, 24, 0, 0)))
				.occasionEnd(Timestamp.valueOf(LocalDateTime.of(2024, 12, 27, 0, 0)))
				.country(countries)
				.season("Winter")
				.type("Public Holiday")
				.description("Christmas is a christian holiday celebrated in winter")
				.build());

		var nonSweCountries = countries.stream().filter(country -> !country.getCountryCode().equals("SWE")).toList().getFirst();
		occasions.add(Occasion.builder()
				.name("Halloween")
				.occasionStart(Timestamp.valueOf(LocalDateTime.of(2024, 10, 31, 0, 0)))
				.occasionEnd(Timestamp.valueOf(LocalDateTime.of(2024, 11, 1, 0, 0)))
				.country(List.of(nonSweCountries))
				.season("Fall")
				.type("Public Holiday")
				.description("Halloween is a ghost festival celebrated in fall")
				.build());

		var swe = countries.stream().filter(country -> country.getCountryCode().equals("SWE")).toList().getFirst();
		occasions.add(Occasion.builder()
				.name("Halloween")
				.occasionStart(Timestamp.valueOf(LocalDateTime.of(2024, 10, 31, 0, 0)))
				.occasionEnd(Timestamp.valueOf(LocalDateTime.of(2024, 11, 1, 0, 0)))
				.country(List.of(swe))
				.season("Fall")
				.type("Working Day")
				.description("Halloween is a ghost festival celebrated in fall")
				.build());
        return occasions;
    }

}
