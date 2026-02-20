package com.example.service.DTO;

import java.util.List;

public record FoyerByStationDTO (

        String address,
        List<MemberOfFoyerDTO> Members

){

}
