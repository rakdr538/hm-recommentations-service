package com.hm.recommendations_service.repository;

import com.hm.recommendations_service.model.DAO.Occasion;
import com.hm.recommendations_service.model.DAO.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface RecipeRepo extends JpaRepository<Recipe, Integer> {
    List<Recipe> findAllByOccasionIn(List<Occasion> occasions);
}
