package com.example.service.DTO;

import java.util.List;

public record FirestationCoverageDTO(
        List<PersonByStationDTO> persons,
        int adultCount,
        int childCount
) {}