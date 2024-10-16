package com.hm.recommendations_service.repository;

import com.hm.recommendations_service.model.DAO.Demographic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DemographicRepo extends JpaRepository<Demographic, Integer> {
    @Query("select d from Demographic d where (d.gender = :gender) and (d.fromAge < :age and d.toAge > :age)")
    List<Demographic> getByGivenAgeAndGender(Integer age, String gender);
}
