package com.example.service;

import com.example.model.Firestation;
import com.example.repository.FirestationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;
    private final DataService dataService;

    public FirestationService(FirestationRepository firestationRepository, DataService dataService) {
        this.firestationRepository = firestationRepository;
        this.dataService = dataService;
    }

    public List<String> getStationByAddress(String station) {
        return firestationRepository.findAll()
                .stream()
                .filter(person -> person.getStation().equalsIgnoreCase(station))
                .map(person -> person.getAddress())
                .collect(Collectors.toList());
    }

    public List<Firestation> findAll() {
        return dataService.getData().getFirestations();
    }

}
