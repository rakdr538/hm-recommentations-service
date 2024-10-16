package com.hm.recommendations_service.repository;

import com.hm.recommendations_service.model.DAO.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CountryRepo extends JpaRepository<Country, Integer> {
    Country findByCountryCode(String countryCode);
    Country findByCountryName(String countryName);
}
