package com.hm.recommendations_service.controller;

import com.hm.recommendations_service.model.DTO.requests.UserPreferences;
import com.hm.recommendations_service.model.DTO.responses.ProductResponseDto;
import com.hm.recommendations_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendationsController {

    private ProductService productService;

    @PostMapping("/recommendations")
    public ResponseEntity<List<ProductResponseDto>> recommendations(
            @Valid @RequestBody UserPreferences userPreferences) {
        return ResponseEntity.ok().body(productService.getProducts(userPreferences));
    }

}
