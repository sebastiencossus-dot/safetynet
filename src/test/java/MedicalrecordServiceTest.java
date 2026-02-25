package com.example.service;

import com.example.model.Medicalrecord;
import com.example.repository.MedicalrecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalrecordServiceTest {

    @Mock
    private MedicalrecordRepository medicalrecordRepository;

    @InjectMocks
    private MedicalrecordService medicalrecordService;


    @Test
    void shouldAddMedicalRecord() {

        // GIVEN
        Medicalrecord record = new Medicalrecord();
        record.setFirstName("Sebastien");
        record.setLastName("Cossus");

        List<Medicalrecord> records = new ArrayList<>();

        when(medicalrecordRepository.findAll())
                .thenReturn(records);

        // WHEN
        Medicalrecord result =
                medicalrecordService.addMedicalRecord(record);

        // THEN
        assertEquals(1, records.size());
        assertEquals(record, result);
    }

    // =========================
    // PUT SUCCESS
    // =========================

    @Test
    void shouldUpdateMedicalRecord() {

        // GIVEN
        Medicalrecord existing = new Medicalrecord();
        existing.setFirstName("Sebastien");
        existing.setLastName("Cossus");
        existing.setBirthdate("01/01/2000");

        Medicalrecord updated = new Medicalrecord();
        updated.setFirstName("Sebastien");
        updated.setLastName("Cossus");
        updated.setBirthdate("02/02/2000");

        List<Medicalrecord> records = new ArrayList<>();
        records.add(existing);

        when(medicalrecordRepository.findAll())
                .thenReturn(records);

        // WHEN
        Medicalrecord result =
                medicalrecordService.updateMedicalRecord(updated);

        // THEN
        assertNotNull(result);
        assertEquals("02/02/2000", records.get(0).getBirthdate());
    }



    @Test
    void shouldReturnNullWhenUpdateNotFound() {

        // GIVEN
        Medicalrecord updated = new Medicalrecord();
        updated.setFirstName("Unknown");
        updated.setLastName("Person");

        when(medicalrecordRepository.findAll())
                .thenReturn(new ArrayList<>());

        // WHEN
        Medicalrecord result =
                medicalrecordService.updateMedicalRecord(updated);

        // THEN
        assertNull(result);
    }

    @Test
    void shouldDeleteMedicalRecord() {

        // GIVEN
        Medicalrecord record = new Medicalrecord();
        record.setFirstName("Sebastien");
        record.setLastName("Cossus");

        List<Medicalrecord> records = new ArrayList<>();
        records.add(record);

        when(medicalrecordRepository.findAll())
                .thenReturn(records);

        // WHEN
        boolean deleted =
                medicalrecordService.deleteMedicalrecord("Sebastien", "Cossus");

        // THEN
        assertTrue(deleted);
        assertEquals(0, records.size());
    }

    @Test
    void shouldReturnFalseWhenDeleteNotFound() {

        // GIVEN
        when(medicalrecordRepository.findAll())
                .thenReturn(new ArrayList<>());

        // WHEN
        boolean deleted =
                medicalrecordService.deleteMedicalrecord("Unknown", "Person");

        // THEN
        assertFalse(deleted);
    }
}