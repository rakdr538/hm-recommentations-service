package com.hm.recommendations_service.model.DTO.requests;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserPreferences {
    private String eventType;
    private Float budget;
    private Integer age;
    private List<String> seasonalStyle;
    private String countryCode;
    private String gender;
}
