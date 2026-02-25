package com.example.controller;

import com.example.model.Person;
import com.example.service.DTO.ChildAlertDTO;
import com.example.service.DTO.PersonInfoDTO;
import com.example.service.DataService;
import com.example.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
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

    @GetMapping("/personInfo")
    public List<PersonInfoDTO> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {

        return personService.getPersonInfo(firstName, lastName);
    }

    @GetMapping("/adressbycity")
    public List<String> getAdressByCity(@RequestParam String city) {
        return  personService.getAdressByCity(city);
    }

    @GetMapping("/childAlert")
    public List<ChildAlertDTO> getChildByAddress(@RequestParam String address) throws Exception{

        return personService.getChildByAddress(address);
    }

    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @PutMapping("/person")
    public Person updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping("/person")
    public String deletePerson(@RequestParam String firstName,
                               @RequestParam String lastName) {

        boolean deleted = personService.deletePerson(firstName, lastName);

        return deleted ? "Person deleted" : "Person not found";
    }

}
