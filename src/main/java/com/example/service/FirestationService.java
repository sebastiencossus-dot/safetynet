package com.example.service;

import com.example.model.Firestation;
import com.example.repository.FirestationRepository;
import com.example.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;
    private final PersonRepository personRepository;


    public FirestationService(FirestationRepository firestationRepository, PersonRepository personRepository) {
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
    }


    public List<String> getStationByAddress(String station) {
        return firestationRepository.findAll()
                .stream()
                .filter(person -> person.getStation().equalsIgnoreCase(station))
                .map(person -> person.getAddress())
                .collect(Collectors.toList());
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
// todo methode pour recupere les personnes a une adresse avec le numero de station, le nom, prenom, phone age et medical
    // Trouver la station via l’adresse
    //
    //Trouver les personnes à cette adresse
    //
    //Calculer l’âge
    //
    //Transformer chaque personne en ResidentDTO
    //
    //Retourner FireAddressDTO
}
