package com.example.repository;

import com.example.model.Firestation;
import com.example.model.Person;
import com.example.service.DataService;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class FirestationRepository {

    private final DataService dataService;

    public FirestationRepository(DataService dataService) {
        this.dataService = dataService;
    }

    public List<Firestation> findAll() {
        return dataService.getData().getFirestations();
    }
}
