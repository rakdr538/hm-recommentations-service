package com.hm.recommendations_service.service;

import com.hm.recommendations_service.model.DAO.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
}
