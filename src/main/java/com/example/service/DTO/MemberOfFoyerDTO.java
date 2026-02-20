package com.example.service.DTO;

import java.util.List;

public record MemberOfFoyerDTO (
        String firstName,
        String lastName,
        int age,
        List<String> medications,
        List<String> allergies
){
}
