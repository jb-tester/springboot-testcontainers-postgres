package com.example.springboottestcontainerspostgres;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonsService personsService;
    private final PersonRepository personRepository;

    public PersonController(PersonsService personsService, PersonRepository personRepository) {
        this.personsService = personsService;
        this.personRepository = personRepository;
    }

    @PostMapping("/init")
    public String init() {
        personsService.initDB();
        return "persons db initialized";
    }

    @GetMapping("/all")
    public Iterable<Person> getAll() {
        return personRepository.findAll();
    }


    @PutMapping("/update/{age:[1-9](?:[0-9])}/{status}")
    public String update(@PathVariable("age") int age, @PathVariable String status) {
        personRepository.updateStatus(status,age);
        return "updated status to "+status+" for persons older than "+age;
    }
}
