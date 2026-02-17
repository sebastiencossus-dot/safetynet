package com.example.controller;

import com.example.model.Person;
import com.example.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    private final DataService dataService;

    public PersonController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return dataService.getData().getPersons();
    }

    @GetMapping("/persons/city")
    public List<Person> getPersonsByCity(String city) {
        return dataService.getData().getPersons()
                .stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .toList();
    }
}
