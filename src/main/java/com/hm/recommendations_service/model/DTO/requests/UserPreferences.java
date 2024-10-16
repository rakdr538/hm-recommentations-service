package com.hm.recommendations_service.model.DTO.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.Set;

@Data
@Builder
public class UserPreferences {

    private String eventType;

    private Float budget;

    @NotNull
    @Max(200)
    @Min(1)
    private Integer age;

    private Set<String> seasonalStyles;

    private String countryCode;

    @NotNull
    @NotEmpty
    @Pattern(regexp="^(male|female)$",message="invalid gender")
    private String gender;
}
