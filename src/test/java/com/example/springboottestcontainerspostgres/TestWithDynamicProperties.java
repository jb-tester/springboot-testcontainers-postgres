package com.example.springboottestcontainerspostgres;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
public class TestWithDynamicProperties {

    // this test uses the @Container with custom configuration and sets the @DynamicProperties
    // the connection properties should be
    // spring.datasource.url=jdbc:postgresql://localhost:<random_port>/ynamicproperties_test
    //spring.datasource.username=test_user
    //spring.datasource.password=test_user

    // connection properties are recognized correctly in this case

    @Container
    static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("dynamicproperties_test")
            .withUsername("test_user")
            .withPassword("test_user");

    @Autowired
    PersonRepository personRepository;
    @Autowired
    private Environment environment;


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Test
    void testPersons() {
        System.out.println(environment.getProperty("spring.datasource.url"));
        personRepository.save(new Person(1,"vasya","pupkin",25,"default"));
        personRepository.save(new Person(2,"vanya","petrov",15,"default"));
        personRepository.save(new Person(3,"valya","sidorov",15,"default"));
        personRepository.save(new Person(4,"petya","ivanov",30,"default"));
        personRepository.save(new Person(5,"pasha","pavlov",33,"default"));
        assertEquals(1, personRepository.findBySurname("ivanov").size());
        personRepository.updateStatus("adult",18);
        assertEquals(3, personRepository.getByStatus("adult").size());
    }
}
