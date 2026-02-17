package com.example.repository;

import com.example.model.Medicalrecord;
import com.example.model.Person;
import com.example.service.DataService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalrecordRepository {

    private final DataService dataService;

    public MedicalrecordRepository(DataService dataService) {
        this.dataService = dataService;
    }

    public List<Medicalrecord> findAll() {
        return dataService.getData().getMedicalrecords();
    }
}
