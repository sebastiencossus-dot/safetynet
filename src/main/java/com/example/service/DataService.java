package com.example.service;

import com.example.model.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Component
public class DataService {


    private Data data;

    public DataService() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("data.json");
        data = mapper.readValue(resource.getInputStream(), Data.class);

    }

    public Data getData() {
        return data;
    }
}