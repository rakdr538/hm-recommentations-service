package com.hm.recommendations_service.repository;

import com.hm.recommendations_service.model.data.Demographic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemographicRepo extends JpaRepository<Demographic, Integer> {

    List<Demographic> findAllByFromAgeAndToAge(Integer fromAge, Integer toAge);
}
