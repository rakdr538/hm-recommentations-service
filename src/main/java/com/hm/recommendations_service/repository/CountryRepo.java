package com.hm.recommendations_service.repository;

import com.hm.recommendations_service.model.DAO.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepo extends JpaRepository<Country, Integer> {
}
