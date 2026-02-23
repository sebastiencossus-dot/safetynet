package com.example.controller;

import com.example.model.Firestation;
import com.example.service.DTO.FirestationCoverageDTO;
import com.example.service.DTO.FoyerByStationDTO;
import com.example.service.DTO.StationAdressDTO;
import com.example.service.DataService;
import com.example.service.FirestationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {

    private final DataService dataService;
    private final FirestationService firestationService;

    public FirestationController(DataService dataService, FirestationService firestationService) {
        this.dataService = dataService;
        this.firestationService = firestationService;
    }

    @GetMapping("/fire-stations")
    public List<Firestation> getAllFirestation() {
        return dataService.getData().getFirestations();
    }

    @GetMapping("/numByAddress")
    public List<String> getStationByAddress(@RequestParam String station) {
        return firestationService.getStationByAddress(station);
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhoneAlert(@RequestParam String station) {
        return firestationService.getPhonesByStation(station);
    }

    @GetMapping("/fire")
    public StationAdressDTO getStationForAddress(@RequestParam String address) throws Exception {
        return firestationService.getStationForAddress(address);
    }

    @GetMapping("/flood/station")
    public List<FoyerByStationDTO> getFlood(
            @RequestParam List<String> station) {

        return FirestationService.getFloodByStations(station);
    }

    @GetMapping("/firestation")
    public FirestationCoverageDTO getFirestation(
            @RequestParam String station) {

        return firestationService.getPersonsByStation(station);
    }

    @PostMapping("/firestation")
    public Firestation add(@RequestBody Firestation firestation) {
        return firestationService.addFirestation(firestation);
    }

    @PutMapping("/firestation")
    public Firestation update(@RequestBody Firestation firestation) {
        return firestationService.updateFirestation(firestation);
    }

    @DeleteMapping("/firestation")
    public String delete(@RequestParam String address) {

        boolean deleted = firestationService.deleteFirestation(address);

        return deleted ? "Firestation deleted" : "Not found";
    }
}

