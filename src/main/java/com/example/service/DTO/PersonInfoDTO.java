package com.example.service.DTO;

import java.lang.reflect.Array;
import java.util.List;

public record PersonInfoDTO (

            String firstName,
            String lastName,
            String address,
            String email,
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
    public String address() {
        return address;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public int age() {
        return age;
    }

    @Override
    public List<String> medications() {
        return medications;
    }

    @Override
    public List<String> allergies() {
        return allergies;
    }
}

