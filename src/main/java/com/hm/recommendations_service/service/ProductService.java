package com.hm.recommendations_service.service;

import com.hm.recommendations_service.model.DAO.Product;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;

public interface ProductService {
    List<Product> getAll();

    List<Product> getMatchingProducts(@NotNull @Max(200) @Min(1) Integer age,
                                      @NotNull @NotEmpty @Pattern(regexp = "^(male|female)$", message = "invalid gender") String gender,
                                      Set<String> seasonalStyles,
                                      String countryCode,
                                      String eventType);
}
