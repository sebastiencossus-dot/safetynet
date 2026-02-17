package com.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
public class Main {
    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode data = mapper.readTree(new File("src/main/resources/data.json"));

            String name = data.get("name").asText();
            String city = data.get("address").get("city").asText();

            System.out.println("Name: " + name);
            System.out.println("City: " + city);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
