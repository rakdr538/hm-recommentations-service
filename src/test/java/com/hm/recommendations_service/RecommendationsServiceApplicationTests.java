package com.hm.recommendations_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hm.recommendations_service.model.DAO.*;
import com.hm.recommendations_service.model.DTO.requests.UserPreferences;
import com.hm.recommendations_service.repository.*;
import com.hm.recommendations_service.service.RecipeServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecommendationsServiceApplicationTests {

	@LocalServerPort
	private Integer port;

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

	@Autowired
	RecipeServiceImpl recipeService;

    private ObjectWriter objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    void createTestData() throws Exception {
        var countries = countryRepo.findAll();
        Assertions.assertEquals(countries.size(), 3);

        occasionRepo.saveAll(getAllOccasions(countries));
        var occasions = occasionRepo.findAll();
        Assertions.assertEquals(occasions.size(), 3);
        var christmas = occasionRepo
                .findByNameAndTypeAndSeason("Christmas", "Public Holiday", "Winter");
        Assertions.assertEquals(3, christmas.getCountries().size());
        var halloweenNotInSwe = occasionRepo
                .findByNameAndTypeAndSeason("Halloween", "Public Holiday", "Fall");
        Assertions.assertEquals(2, halloweenNotInSwe.getCountries().size());
        var halloweenInSwe = occasionRepo
                .findByNameAndTypeAndSeason("Halloween", "Working Day", "Fall");
        Assertions.assertEquals(1, halloweenInSwe.getCountries().size());

        var demographics = demographicRepo.findAll();
        Assertions.assertEquals(demographics.size(), 5);

        categoryRepo.saveAll(getAllCategories(demographics));
        var categories = categoryRepo.findAll();
        Assertions.assertEquals(categories.size(), 4);
        var adultCat = categoryRepo.findByName("Adults Clothes");
        Assertions.assertEquals(1, adultCat.getDemographics().size());
		var kidsCat = categoryRepo.findByName("Kids Clothes");
		Assertions.assertEquals(3, kidsCat.getDemographics().size());

        var variations = variationRepo.findAll();
        Assertions.assertEquals(8, variations.size());

		// building DB for products
        productRepo.saveAll(getAdultsHalloweenProductsInSweden(variations, adultCat, halloweenInSwe));
        productRepo.saveAll(getKidsChristmasProducts(variations, kidsCat, christmas));
		var allProducts = productRepo.findAll();
		Assertions.assertEquals(7 ,allProducts.size());

		var halloweenProducts = allProducts.stream()
				.filter(p -> p.getOccasion().contains(halloweenInSwe))
						.toList();

		// building DB for recipe
		var halloweenRecipeForAdultMales = recipeService.create("Halloween for Adults in Sweden",
				halloweenProducts,
				halloweenInSwe);
		Assertions.assertNotNull(halloweenRecipeForAdultMales);

		// building DB for recipe
		var winterOccasion = occasions.stream()
				.filter(o -> o.getSeason().equals("Winter")).toList();
		var winterProducts = allProducts.stream()
				.filter(p -> p.getOccasion().containsAll(winterOccasion))
				.toList();
		var winterRecipeForKids = recipeService.create("Winter clothes for kids in Sweden",
				winterProducts,
				halloweenInSwe);
		Assertions.assertNotNull(winterRecipeForKids);

        var userPref = UserPreferences.builder()
                .age(34)
                .gender("Male")
                .budget(2000F)
                .eventType("Halloween")
                .build();

        var result = mockMvc.perform(post("/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPref)))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Result: " + result.getResponse().getContentAsString());

    }

	private List<Product> getKidsChristmasProducts(List<Variation> variations,
												   Category kidsCat,
												   Occasion christmas) {
		List<Product> kidsChristmasProducts = new ArrayList<>();

		var kidsVariations = variations.stream().filter(v ->
					(v.getName().equals("shirt") && v.getProductColor().equals("red")) ||
					(v.getName().equals("socks") && v.getProductColor().equals("red")) ||
					(v.getName().equals("pants") && v.getProductColor().equals("red")))
				.toList();

		Assertions.assertEquals(3, kidsVariations.size());

		kidsVariations.forEach(kv -> kidsChristmasProducts.add(Product.builder()
				.variation(kv)
				.category(List.of(kidsCat))
				.occasion(List.of(christmas))
				.discountedPrice(kv.getOriginalPrice())
				.inStock(kv.getSku() > 0)
				.build()));

		return kidsChristmasProducts;
	}

	private List<Product> getAdultsHalloweenProductsInSweden(List<Variation> variations,
															 Category adultCat,
															 Occasion halloweenInSwe) {
        List<Product> adultHalloweenProductsInSweden = new ArrayList<>();

        var adultVariations = variations.stream().filter(v ->
                        v.getName().equals("Monster Dress") ||
                        v.getName().equals("Spider-man Dress") ||
                        (v.getName().equals("socks") && v.getProductColor().equals("black")) ||
                        (v.getName().equals("pants") && v.getProductColor().equals("black")))
                .toList();

        Assertions.assertEquals(4, adultVariations.size());

        adultVariations.forEach(av -> adultHalloweenProductsInSweden.add(Product.builder()
                .variation(av)
                .category(List.of(adultCat))
                .occasion(List.of(halloweenInSwe))
                .discountedPrice(av.getOriginalPrice())
                .inStock(av.getSku() > 0)
                .build()));
        return adultHalloweenProductsInSweden;
    }

    private List<Category> getAllCategories(List<Demographic> demographics) {
        var categories = new ArrayList<Category>();
        var kids = demographics.stream().filter(d -> d.getToAge() <= 18).toList();
        Assertions.assertEquals(3, kids.size());
        categories.add(Category.builder()
                .name("Kids Clothes")
                .demographics(kids)
                .description("For kids")
                .image_url("some_image")
                .thumbnail_url("some_thumbnail_url")
                .build());
        categories.add(Category.builder()
                .name("Kids Shoes")
                .demographics(kids)
                .description("For kids shoes")
                .image_url("some_image")
                .thumbnail_url("some_thumbnail_url")
                .build());
        categories.add(Category.builder()
                .name("Kids Accessories")
                .description("For kids accessories")
                .image_url("some_image")
                .thumbnail_url("some_thumbnail_url")
                .build());

        var adults = demographics.stream()
                .filter(d -> d.getFromAge() >= 18 && d.getToAge() < 65 && d.getGender().equals("male"))
                .toList();
        Assertions.assertEquals(1, adults.size());
        categories.add(Category.builder()
                .name("Adults Clothes")
                .description("For grown-ups")
                .image_url("some_image_url_adults")
                .thumbnail_url("some_thumbnail_url_for_adults")
                .demographics(adults)
                .build());
        return categories;
    }

    private List<Occasion> getAllOccasions(List<Country> countries) {
        var occasions = new ArrayList<Occasion>();
        var occasion1 = Occasion.builder()
                .name("Christmas")
                .occasionStart(Timestamp.valueOf(LocalDateTime.of(2024, 12, 24, 0, 0)))
                .occasionEnd(Timestamp.valueOf(LocalDateTime.of(2024, 12, 27, 0, 0)))
                .season("Winter")
                .type("Public Holiday")
                .description("Christmas is a christian holiday celebrated in winter")
                .countries(countries)
                .build();
        occasions.add(occasion1);

        var nonSweCountries = countries.stream().filter(country -> !country.getCountryCode().equals("SWE")).toList();
        var occasion2 = Occasion.builder()
                .name("Halloween")
                .occasionStart(Timestamp.valueOf(LocalDateTime.of(2024, 10, 31, 0, 0)))
                .occasionEnd(Timestamp.valueOf(LocalDateTime.of(2024, 11, 1, 0, 0)))
                .season("Fall")
                .type("Public Holiday")
                .description("Halloween is a ghost festival celebrated in fall")
                .countries(nonSweCountries)
                .build();
        occasions.add(occasion2);

        var swe = countries.stream().filter(country -> country.getCountryCode().equals("SWE")).toList().getFirst();
        var occasion3 = Occasion.builder()
                .name("Halloween")
                .occasionStart(Timestamp.valueOf(LocalDateTime.of(2024, 10, 31, 0, 0)))
                .occasionEnd(Timestamp.valueOf(LocalDateTime.of(2024, 11, 1, 0, 0)))
                .season("Fall")
                .type("Working Day")
                .description("Halloween is a ghost festival celebrated in fall")
                .countries(List.of(swe))
                .build();
        occasions.add(occasion3);
        return occasions;
    }

}
