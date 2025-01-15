package com.example.springboottestcontainerspostgres;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonsService {

//
   private final PersonRepository personRepository;

   public PersonsService(PersonRepository personRepository) {
      this.personRepository = personRepository;
   }

   public void initDB(){

       List<Person> all = (List<Person>) personRepository.findAll();
       int count = 0;
       if (!all.isEmpty()) {
           count = all.size();
       }
///
       personRepository.save(new Person(count+1,"vasya","pupkin",25,"default"));
       personRepository.save(new Person(count+2,"vanya","petrov",15,"default"));
       personRepository.save(new Person(count+3,"valya","sidorov",15,"default"));
       personRepository.save(new Person(count+4,"petya","ivanov",30,"default"));
       personRepository.save(new Person(count+5,"pasha","pavlov",33,"default"));
       personRepository.save(new Person(count+6,"pasha","ivanov",70,"default"));
       personRepository.save(new Person(count+7,"sasha","pavlova",63,"default"));

   }



}
