package com.hm.recommendations_service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		var container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
		container.withDatabaseName("hm").withUsername("root").withPassword("postgres");
		container.withCopyFileToContainer(
				MountableFile.forClasspathResource("init-db.sql"),
				"/docker-entrypoint-initdb.d/"
		);
		return container;
	}

}
