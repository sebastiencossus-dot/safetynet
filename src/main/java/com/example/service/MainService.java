package com.example.service;

import com.example.repository.FirestationRepository;
import com.example.repository.MedicalrecordRepository;
import com.example.repository.PersonRepository;
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
}




