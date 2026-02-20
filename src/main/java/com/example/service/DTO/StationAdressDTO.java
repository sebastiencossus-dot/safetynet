package com.example.service.DTO;

import java.util.List;

public record StationAdressDTO (
        String station,
        List<ResidentsDTO> residents
) {
//    @Override
//    public String station() {
//        return station;
//    }
//
//    @Override
//    public List<ResidentsDTO> residents() {
//        return residents;
//    }
}
