package com.example.controller;

import com.example.model.Person;
import com.example.service.DataService;
import com.example.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    private final DataService dataService;
    private final PersonService personService;

    public PersonController(DataService dataService, PersonService personService) {
        this.dataService = dataService;
        this.personService = personService;
    }

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return dataService.getData().getPersons();
    }

    @GetMapping("/emailbycity")
    public List<String> getEmailByCity(@RequestParam String city) {
        return personService.getEmailsByCity(city);
    }

    @GetMapping("/adressbycity")
    public List<String> getAdressByCity(@RequestParam String city) {
        return  personService.getAdressByCity(city);
    }
}
