package com.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Reads a JSON file from a relative path and parses it into a JsonNode.
     *
     * @param filePath The relative path to the JSON file.
     * @return A JsonNode representing the file's content.
     * @throws IOException if an I/O error occurs reading from the file.
     */
    public static JsonNode readJsonFromFile(String filePath) throws IOException {
        String content = Files.readString(Path.of(filePath));
        return objectMapper.readTree(content);
    }
}
