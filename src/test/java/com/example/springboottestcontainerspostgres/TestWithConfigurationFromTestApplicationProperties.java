package com.example.springboottestcontainerspostgres;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class TestWithConfigurationFromTestApplicationProperties {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    private Environment environment;


// This test should use the datasource configured in the test application.properties, i.e.
// the H2 in-memo database with ' jdbc:h2:mem:testdb ' url

    // connection properties are recognized correctly in this case

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
