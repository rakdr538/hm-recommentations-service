package com.hm.recommendations_service.repository;

import com.hm.recommendations_service.model.DAO.Variation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariationRepo extends JpaRepository<Variation, Integer> {
}
