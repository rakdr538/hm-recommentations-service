package com.hm.recommendations_service.repository;

import com.hm.recommendations_service.model.DAO.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepo extends JpaRepository<Recipe, Integer> {
}
