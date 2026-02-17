package com.example.model;

import java.util.List;

public class Data {
    private List<Person> persons;
    private List<Firestation> firestations;

    public List<Medicalrecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<Medicalrecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Firestation> firestations) {
        this.firestations = firestations;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    private List<Medicalrecord> medicalrecords;

}
