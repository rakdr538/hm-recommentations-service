package com.hm.recommendations_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecommendationsController {

    @PostMapping("/recommendations")
    public ResponseEntity<String> recommendations() {
        return ResponseEntity.ok("Hi Rakesh");
    }

}
