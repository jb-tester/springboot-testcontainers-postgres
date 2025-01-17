package com.example.springboottestcontainerspostgres;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // deactivate the default behaviour
@DataJpaTest
public class TestWithServiceConnectionBean {

    // this test uses the customized @ServiceConnection the inner config class
    // the connection properties should be
    // spring.datasource.url=jdbc:postgresql://localhost:<random_port>/customTest2
    //spring.datasource.username=sa
    //spring.datasource.password=sa

    // !!! instead, the connection properties from test application.properties are used




    @Autowired
    PersonRepository personRepository;





    @Test
    void testPersons() {
        personRepository.save(new Person(1,"vasya","pupkin",25,"default"));
        personRepository.save(new Person(2,"vanya","petrov",15,"default"));
        personRepository.save(new Person(3,"valya","sidorov",15,"default"));
        personRepository.save(new Person(4,"petya","ivanov",30,"default"));
        personRepository.save(new Person(5,"pasha","pavlov",33,"default"));
        assertEquals(1, personRepository.findBySurname("ivanov").size());
        personRepository.findAll().forEach(System.out::println);
        personRepository.updateStatus("adult",18);
        assertEquals(3, personRepository.getByStatus("adult").size());
    }
    @TestConfiguration
    public static class ThisTestConfiguration {

        @Bean
        @ServiceConnection
        PostgreSQLContainer<?> postgresContainerIntegration() {
            PostgreSQLContainer container = new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("customTest2")
                    .withUsername("sa")
                    .withPassword("sa");
            container.start();
            System.out.println("TestWithServiceConnectionBean: " + container.getJdbcUrl());
            return container
                    ;
        }
    }
}
