package com.example.service.DTO;

public record PersonByStationDTO(
        String firstName,
        String lastName,
        String address,
        String phone
) {}