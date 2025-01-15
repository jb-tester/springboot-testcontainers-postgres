package com.example.springboottestcontainerspostgres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@Import(TestConfiguration.class)
public class TestApplicationWithDefaultImportedTestcontainerConfiguration {


    // this test uses the default (not customized) @ServiceConnection bean from the TestcontainersConfiguration class
    // the connection properties should be
    // spring.datasource.url=jdbc:postgresql://localhost:<random_port>/test
    //spring.datasource.username=test
    //spring.datasource.password=test

    // !!! instead, the connection properties from test application.properties are used

    public static void main(String[] args) {
        SpringApplication.from(SpringbootTestcontainersPostgresApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
