package com.example.controller;

import com.example.service.DTO.PersonInfoDTO;
import com.example.service.MainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhoneAlert(@RequestParam String station) {
        return mainService.getPhonesByStation(station);
    }

    @GetMapping("/personInfo")
    public List<PersonInfoDTO> getPersonInfo(
            @RequestParam String firstName,
            @RequestParam String lastName) {

        return mainService.getPersonInfo(firstName, lastName);
    }
}

