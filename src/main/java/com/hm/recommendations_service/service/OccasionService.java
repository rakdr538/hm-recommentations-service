package com.hm.recommendations_service.service;

import com.hm.recommendations_service.model.DAO.Occasion;

import java.util.List;
import java.util.Set;

public interface OccasionService {
    List<Occasion> getOccasions(String countryCode, Set<String> seasonalStyles, String eventType);
    Occasion getOccasion();
}
