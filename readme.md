
## Check the datasource connection properties recognition in the Spring Debugger

actually the cases when the local connection properties from src or test `application.properties`
or testcontainer configured via `@Container`+`@DynamicProperties` are treated correctly;
but the testcontainers configured by `@ServiceConnection` beans are not treated properly


1. the main application class `com.example.springboottestcontainerspostgres.SpringbootTestcontainersPostgresApplication`:
    should use the datasource properties from src `application.properties` - OK
2. the test class that uses the main configuration context `com.example.springboottestcontainerspostgres.TestWithConfigurationFromTestApplicationProperties`
    should use the datasource properties from test `application.properties` - OK
3. the test class that makes use of testcontainers via `@Container` plus `@DynamicProperties` (`com.example.springboottestcontainerspostgres.TestWithDynamicProperties`)
    should use the connection properties configured by container configuration - OK
4. default `@ServiceConnection` bean in the imported configuration (`com.example.springboottestcontainerspostgres.TestApplicationWithDefaultImportedTestcontainerConfiguration`)
    should use the default testcontainer configuration, but uses the connection properties from test `application.properties`
5. customized `@ServiceConnection` bean in the same test class (`com.example.springboottestcontainerspostgres.TestWithCustomizedServiceConnectionBeanContainer`)
    should use the custom testcontainer configuration, but uses the connection properties from test `application.properties`