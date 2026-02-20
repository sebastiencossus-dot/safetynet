package com.example.service.DTO;

import java.util.List;

public record ChildAlertDTO (

    String firstName,
    String lastName,
    int age,
    List<String> foyer
){

}
