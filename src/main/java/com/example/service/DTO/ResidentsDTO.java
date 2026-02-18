package com.example.service.DTO;

public record ResidentsDTO (
        String firstName,
        String lastName,
        String phone,
        int age

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
