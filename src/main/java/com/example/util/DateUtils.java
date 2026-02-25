package com.example.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private DateUtils() {
        // Empêche l’instanciation
    }

    public static int calculateAge(String birthdate) {
        LocalDate birth = LocalDate.parse(birthdate, FORMATTER);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}