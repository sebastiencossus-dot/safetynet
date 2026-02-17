package com.example.controller;

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
}

