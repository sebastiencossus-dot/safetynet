package com.example.service;

import com.example.model.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DataService {

    private final ObjectMapper mapper;
    private Data data;

    public DataService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void loadData() throws IOException {
        ClassPathResource resource = new ClassPathResource("data.json");
        data = mapper.readValue(resource.getInputStream(), Data.class);
        System.out.println("Data loaded successfully");
    }

    public Data getData() {
        return data;
    }
}