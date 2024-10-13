package com.hm.recommendations_service.service;

import com.hm.recommendations_service.model.DAO.Demographic;
import com.hm.recommendations_service.repository.DemographicRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemographicServiceImpl implements DemographicService {

    private final DemographicRepo demographicRepo;

    @Override
    public List<Demographic> getAll() {
        return demographicRepo.findAll();
    }

    @Override
    public Demographic getById(Integer id) {
        return null;
    }

    @Override
    public List<Demographic> getByAgeGroup(Integer fromAge, Integer toAge) {
        return demographicRepo.findAllByFromAgeAndToAge(fromAge, toAge);
    }
}
