package com.example.controller;

import com.example.model.Medicalrecord;

import com.example.service.MedicalrecordService;

import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalrecordController {

    private final MedicalrecordService medicalrecordService;

    public MedicalrecordController(MedicalrecordService medicalrecordService) {
        this.medicalrecordService = medicalrecordService;
    }

    @PostMapping("/medicalRecord")
    public Medicalrecord add(@RequestBody Medicalrecord medicalrecord) {
        return medicalrecordService.addMedicalRecord(medicalrecord);
    }

    @PutMapping("/medicalRecord")
    public Medicalrecord update(@RequestBody Medicalrecord medicalrecord) {
        return medicalrecordService.updateMedicalRecord(medicalrecord);
    }

    @DeleteMapping("/medicalRecord")
    public String delete(@RequestParam String firstName,
                         @RequestParam String lastName) {

        boolean deleted = medicalrecordService.deleteMedicalrecord(firstName, lastName);

        return deleted ? "MedicalRecord deleted" : "Not found";
    }
}



