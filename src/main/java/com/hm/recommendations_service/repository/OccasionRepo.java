package com.hm.recommendations_service.repository;

import com.hm.recommendations_service.model.DAO.Occasion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccasionRepo extends JpaRepository<Occasion, Integer> {
}
