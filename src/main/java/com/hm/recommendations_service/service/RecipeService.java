package com.hm.recommendations_service.service;

import com.hm.recommendations_service.model.DAO.Occasion;
import com.hm.recommendations_service.model.DAO.Product;
import com.hm.recommendations_service.model.DAO.Recipe;

import java.util.List;

public interface RecipeService {
    Recipe create(String recipeName, List<Product> products, Occasion occasion);
}
