package com.hm.recommendations_service.service;

import com.hm.recommendations_service.model.DAO.Occasion;
import com.hm.recommendations_service.repository.CountryRepo;
import com.hm.recommendations_service.repository.OccasionRepo;
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
public class OccasionServiceImpl implements OccasionService{

    private final OccasionRepo occasionRepo;
    private final CountryRepo countryRepo;

    @Override
    public List<Occasion> getOccasions(String countryCode, Set<String> seasonalStyles, String eventType) {

        // TODO using brut force, need to use criteria builder here.

        var occasions = new ArrayList<Occasion>();
        if(isString(countryCode)) {
            var c = countryRepo.findByCountryCode(countryCode);
            occasions.addAll(occasionRepo.findAll().stream().filter( o -> o.getCountries().contains(c)).toList());
        }
        if(isValidSet(seasonalStyles)) {
            occasions.addAll(occasionRepo.findAllBySeasonIn(seasonalStyles));
        }
        if(isString(eventType)) {
            occasions.addAll(occasionRepo.findAllByName(eventType));
        }

        return occasions;
    }

    @Override
    public Occasion getOccasion() {
        return null;
    }

    private boolean isValidSet(Collection<String> set) {
        if(CollectionUtils.isEmpty(set)){
            return false;
        }
        return !CollectionUtils.isEmpty(set.stream().filter(this::isString).collect(Collectors.toSet()));
    }

    private boolean isString(String s) {
        return StringUtils.hasText(s);
    }
}
