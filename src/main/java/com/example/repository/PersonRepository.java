package com.example.repository;

import com.example.model.Person;
import com.example.service.DataService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository {

    private final DataService dataService;

    public PersonRepository(DataService dataService) {
        this.dataService = dataService;
    }

    public List<Person> findAll() {
        return dataService.getData().getPersons();
    }
}

