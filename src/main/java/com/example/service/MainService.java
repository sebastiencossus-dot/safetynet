package com.example.service;

import com.example.repository.FirestationRepository;
import com.example.repository.MedicalrecordRepository;
import com.example.repository.PersonRepository;
import com.example.service.DTO.PersonInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MainService {

    private final FirestationRepository firestationRepository;
    private final PersonRepository personRepository;
    private final MedicalrecordRepository medicalrecordRepository;

    public MainService(FirestationRepository firestationRepository,
                       PersonRepository personRepository, MedicalrecordRepository medicalrecordRepository) {
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
        this.medicalrecordRepository = medicalrecordRepository;
    }

    public List<String> getPhonesByStation(String station) {


        Set<String> addresses = firestationRepository.findAll()
                .stream()
                .filter(f -> f.getStation().equals(station))
                .map(f -> f.getAddress())
                .collect(Collectors.toSet());


        return personRepository.findAll()
                .stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .map(p -> p.getPhone())
                .distinct()
                .toList();
    }

    //todo ici faire la class pour AllPersonsWithMedical
    // doit retourner nom, prenom, adresse, email, age avec les medicaments et les allergies
    // utiliser un DTO
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
}




