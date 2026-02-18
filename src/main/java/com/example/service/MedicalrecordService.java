package com.example.service;

import com.example.repository.MedicalrecordRepository;
import com.example.repository.PersonRepository;

public class MedicalrecordService {

    private final PersonRepository personRepository;
    private final MedicalrecordRepository medicalrecordRepository;


    public MedicalrecordService(PersonRepository personRepository, MedicalrecordRepository medicalrecordRepository) {
        this.personRepository = personRepository;
        this.medicalrecordRepository = medicalrecordRepository;
    }


}
