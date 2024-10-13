package com.hm.recommendations_service.repository;

import com.hm.recommendations_service.TestcontainersConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@Import(TestcontainersConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class DemographicRepoTest {

    @Autowired
    DemographicRepo demographicRepo;

    @Test
    void testDataInDemographicsTable() {
        var result = demographicRepo.findAll();
        Assertions.assertEquals(6, result.size());
    }
}