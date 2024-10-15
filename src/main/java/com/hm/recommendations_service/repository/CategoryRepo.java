package com.hm.recommendations_service.repository;

import com.hm.recommendations_service.model.DAO.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Category findByName(String name);
}
