package com.hm.recommendations_service.controller;

import com.hm.recommendations_service.model.DTO.requests.UserPreferences;
import com.hm.recommendations_service.model.DTO.responses.UserPreferenceResponseDto;
import com.hm.recommendations_service.usecases.GetRecipesAndProductsAsPerUserRequestUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendationsController {

    private final GetRecipesAndProductsAsPerUserRequestUseCase getRecipesAndProductsAsPerUserRequest;

    @PostMapping("/recommendations")
    public ResponseEntity<List<UserPreferenceResponseDto>> recommendations(
            @Valid @RequestBody UserPreferences userPreferences) {
        return ResponseEntity.ok().body(getRecipesAndProductsAsPerUserRequest.getProducts(userPreferences));
    }

    @GetMapping("/hello")
    public String hello() {
        return "Well Done!";
    }
}
