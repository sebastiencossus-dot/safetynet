package com.example.service;

import com.example.model.Person;
import com.example.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

   private final PersonRepository personRepository;
    private final DataService dataService;

    public PersonService(PersonRepository personRepository, DataService dataService) {
       this.personRepository = personRepository;
        this.dataService = dataService;
    }

   public List<String> getEmailsByCity(String city) {
       return personRepository.findAll()
               .stream()
               .filter(person -> person.getCity().equalsIgnoreCase(city))
               .map(person -> person.getEmail())
               .distinct()
               .collect(Collectors.toList());
   }

   public List<String> getAdressByCity(String city) {
       return personRepository.findAll()
               .stream()
               .filter(person -> person.getCity().equalsIgnoreCase(city))
               .map(person -> person.getAddress())
               .distinct()
               .collect(Collectors.toList());
   }

   public List<Person> findAll() {
       return dataService.getData().getPersons();
   }
}
