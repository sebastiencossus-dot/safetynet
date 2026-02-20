package com.example.service.DTO;

import java.util.List;

public record ResidentsDTO (
        String firstName,
        String lastName,
        String phone,
        int age,
        List<String> medications,
        List<String> allergies

) {


    @Override
    public String firstName() {
        return firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }

    @Override
    public String phone() {
        return phone;
    }

    @Override
    public int age() {
        return age;
    }
}
