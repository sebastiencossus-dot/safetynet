package com.example.controller;

import com.example.model.Firestation;
import com.example.model.Person;
import com.example.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class FirestationController {

    private final DataService dataService;

    public FirestationController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/firestations")
    public List<Firestation> getAllFirestations() {
        return dataService.getData().getFirestations();
    }


}

