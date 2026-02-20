package com.example.service;

import com.example.model.Person;
import com.example.repository.FirestationRepository;
import com.example.repository.MedicalrecordRepository;
import com.example.repository.PersonRepository;
import com.example.service.DTO.PersonInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

   private final PersonRepository personRepository;
   private final MedicalrecordRepository medicalrecordRepository;
   private final FirestationRepository firestationRepository;

    public PersonService(PersonRepository personRepository, MedicalrecordRepository medicalrecordRepository, FirestationRepository firestationRepository) {
       this.personRepository = personRepository;
       this.medicalrecordRepository = medicalrecordRepository;
       this.firestationRepository = firestationRepository;
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

   public List<PersonInfoDTO> getPersonInfo(String firstName, String lastName) {

        return personRepository.findAll()
                .stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName))
                .map(person -> {

                    var medicalRecord = medicalrecordRepository.findAll()
                            .stream()
                            .filter(m -> m.getFirstName().equalsIgnoreCase(person.getFirstName())
                                    && m.getLastName().equalsIgnoreCase(person.getLastName()))
                            .findFirst()
                            .orElse(null);

                    int age = 0;

                    if (medicalRecord != null) {
                        age = calculateAge(medicalRecord.getBirthdate());
                    }

                    return new PersonInfoDTO(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getAddress(),
                            person.getEmail(),
                            age,
                            medicalRecord != null ? medicalRecord.getMedications() : List.of(),
                            medicalRecord != null ? medicalRecord.getAllergies() : List.of()
                    );
                })
                .toList();
    }

   private int calculateAge(String birthdate) {
        java.time.LocalDate birth = java.time.LocalDate.parse(
                birthdate,
                java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy")
        );
        return java.time.Period.between(birth, java.time.LocalDate.now()).getYears();
    }

   //todo faire liste d'enfants ( -18 ) habitant a l'adresse et retourner prenom, nom, age et liste des autres membres du foyer
   public List<.......> getChildByAddress(String address) {

        //recherche des person a l'adresse

       // recherche des mineurs

       // ajout du reste du foyer
   }
}
