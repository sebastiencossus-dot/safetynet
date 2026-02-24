package com.example.service;

import com.example.model.Firestation;
import com.example.model.Medicalrecord;

import com.example.repository.FirestationRepository;
import com.example.repository.MedicalrecordRepository;
import com.example.repository.PersonRepository;
import com.example.service.DTO.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

@Service
public class FirestationService {

    private static FirestationRepository firestationRepository = null;
    private static PersonRepository personRepository = null;
    private static MedicalrecordRepository medicalrecordRepository = null;


    public FirestationService(FirestationRepository firestationRepository, PersonRepository personRepository, MedicalrecordRepository medicalrecordRepository) {
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
        this.medicalrecordRepository = medicalrecordRepository;
    }


    public List<String> getStationByAddress(String station) {
        return firestationRepository.findAll().stream().filter(person -> person.getStation().equalsIgnoreCase(station)).map(person -> person.getAddress()).collect(Collectors.toList());
    }

    public List<String> getPhonesByStation(String station) {


        Set<String> addresses = firestationRepository.findAll().stream().filter(f -> f.getStation().equals(station)).map(f -> f.getAddress()).collect(Collectors.toSet());


        return personRepository.findAll().stream().filter(p -> addresses.contains(p.getAddress())).map(p -> p.getPhone()).distinct().toList();
    }

    public StationAdressDTO getStationForAddress(String address) throws Exception {
        // Trouver la station via l’adresse
        Optional<Firestation> firestation = firestationRepository.findAll().stream().filter(f -> f.getAddress().equalsIgnoreCase(address)).findFirst();

        if (firestation.isEmpty()) {
            throw new Exception("Pas de caserne a cette adresse");
        }

        //Trouver les personnes à cette adresse
        List<Medicalrecord> medicalrecords = medicalrecordRepository.findAll();

        List<ResidentsDTO> residents = personRepository.findAll().stream().filter(p -> p.getAddress().equalsIgnoreCase(address)).map(person -> {

            Optional<Medicalrecord> medicalrecord = medicalrecords.stream().filter(m -> m.getFirstName().equalsIgnoreCase(person.getFirstName()) && m.getLastName().equalsIgnoreCase(person.getLastName())).findFirst();
            //Calculer l’âge
            int age = medicalrecord.isEmpty() ? 0 : calculateAge(medicalrecord.get().getBirthdate());

            //Transformer chaque personne en ResidentDTO
            return new ResidentsDTO(person.getFirstName(), person.getLastName(), person.getPhone(), age, medicalrecord != null ? medicalrecord.get().getMedications() : List.of(), medicalrecord != null ? medicalrecord.get().getAllergies() : List.of());
        }).toList();
        //Retourner StationAdressDTO
        return new StationAdressDTO(firestation.get().getStation(), residents);
    }

    public static int calculateAge(String birthdate) {
        LocalDate birth = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return Period.between(birth, LocalDate.now()).getYears();
    }


    public static List<FoyerByStationDTO> getFloodByStations(List<String> stations) {
        var firestations = firestationRepository.findAll();
        var persons = personRepository.findAll();
        var medicalRecords = medicalrecordRepository.findAll();

        // Récupérer les adresses couvertes
        var addresses = firestations
                .stream()
                .filter(f -> stations.contains(f.getStation()))
                .map(f -> f.getAddress()).toList();

        // Grouper les personnes par adresse
        return addresses
                .stream().distinct()
                .map(address -> {


                    var residents = persons.stream().filter(p -> p.getAddress().equalsIgnoreCase(address)).map(person -> {


                        var medicalRecord = medicalRecords.stream().filter(m -> m.getFirstName().equalsIgnoreCase(person.getFirstName()) && m.getLastName().equalsIgnoreCase(person.getLastName())).findFirst().orElse(null);


                        int age = medicalRecord != null ? calculateAge(medicalRecord.getBirthdate()) : 0;


                        return new MemberOfFoyerDTO(person.getFirstName(), person.getLastName(), age, medicalRecord != null ? medicalRecord.getMedications() : List.of(), medicalRecord != null ? medicalRecord.getAllergies() : List.of());

                    }).toList();

                return new FoyerByStationDTO(address, residents);
            }).toList();
    }


    public FirestationCoverageDTO getPersonsByStation(String station) {

        var firestations = firestationRepository.findAll();
        var persons = personRepository.findAll();
        var medicalRecords = medicalrecordRepository.findAll();

        // Récupérer les adresses couvertes par la station
        var addresses = firestations.stream()
                .filter(f -> f.getStation().equals(station))
                .map(f -> f.getAddress())
                .toList();

        // Récupérer les personnes vivant à ces adresses
        var coveredPersons = persons.stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .toList();

        int adultCount = 0;
        int childCount = 0;

        // Calculer le nombre d’adultes et d’enfants
        for (var person : coveredPersons) {

            var medicalRecord = medicalRecords.stream()
                    .filter(m -> m.getFirstName().equalsIgnoreCase(person.getFirstName())
                            && m.getLastName().equalsIgnoreCase(person.getLastName()))
                    .findFirst()
                    .orElse(null);

            int age = medicalRecord != null
                    ? calculateAge(medicalRecord.getBirthdate())
                    : 0;

            if (age < 18) {
                childCount++;
            } else {
                adultCount++;
            }
        }

        // Construire la liste des personnes (sans âge)
        var personDTOList = coveredPersons.stream()
                .map(person -> new PersonByStationDTO(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        person.getPhone()
                ))
                .toList();

        // Retourner le DTO final
        return new FirestationCoverageDTO(
                personDTOList,
                adultCount,
                childCount
        );
    }

    public Firestation addFirestation(Firestation firestation) {

        firestationRepository.findAll().add(firestation);

        return firestation;
    }

    // PUT
    public Firestation updateFirestation(Firestation updatedFirestation) {

        var firestations = firestationRepository.findAll();

        for (int i = 0; i < firestations.size(); i++) {

            Firestation existing = firestations.get(i);

            if (existing.getAddress().equalsIgnoreCase(updatedFirestation.getAddress())) {
                firestations.set(i, updatedFirestation);
                return updatedFirestation;
            }
        }

        return null;
    }

    // DELETE
    public boolean deleteFirestation(String address) {

        return firestationRepository.findAll()
                .removeIf(f -> f.getAddress().equalsIgnoreCase(address));
    }
}



