package com.hm.recommendations_service.service;

import com.hm.recommendations_service.model.DAO.Demographic;

import java.util.List;

public interface DemographicService {

    List<Demographic> getAll();
    Demographic getById(Integer id);
    List<Demographic> getByAgeGroup(Integer fromAge, Integer toAge);
}
