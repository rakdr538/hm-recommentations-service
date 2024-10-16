package com.hm.recommendations_service.repository;

import com.hm.recommendations_service.model.DAO.Occasion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface OccasionRepo extends JpaRepository<Occasion, Integer> {
    Occasion findByNameAndTypeAndSeason(String name, String type, String season);

    List<Occasion> findAllByName(String name);

    List<Occasion> findAllBySeasonIn(Set<String> seasonalStyle);

    // TODO this is not giving appropriate result hence switching to fallback scenario
    @Query("SELECT a, b FROM Occasion a JOIN a.countries b")
    List<Occasion> getAllByCountry(String countryCode);
}
