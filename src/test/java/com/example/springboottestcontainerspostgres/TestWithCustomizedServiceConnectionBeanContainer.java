package com.example.springboottestcontainerspostgres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestWithCustomizedServiceConnectionBeanContainer {

    // this test uses the customized @ServiceConnection the current class
    // the connection properties should be
    // spring.datasource.url=jdbc:postgresql://localhost:<random_port>/customTest
    //spring.datasource.username=sa
    //spring.datasource.password=sa

    // !!! instead, the connection properties from test application.properties are used

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainerIntegration() {
        PostgreSQLContainer container = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("customTest")
                .withUsername("sa")
                .withPassword("sa");
        container.start();
        System.out.println("TestWithCustomizedServiceConnectionBeanContainer" + container.getJdbcUrl());
        return container
                ;
    }

    public static void main(String[] args) {
        SpringApplication.from(SpringbootTestcontainersPostgresApplication::main).with(TestWithCustomizedServiceConnectionBeanContainer.class).run(args);
    }

}
