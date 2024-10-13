package com.hm.recommendations_service.repository;

import com.hm.recommendations_service.model.DAO.Demographic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemographicRepo extends JpaRepository<Demographic, Integer> {

    List<Demographic> findAllByFromAgeAndToAge(Integer fromAge, Integer toAge);
}
