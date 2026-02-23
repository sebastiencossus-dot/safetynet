package com.example.service;

import com.example.model.Medicalrecord;
import com.example.repository.MedicalrecordRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicalrecordService {

    private final MedicalrecordRepository medicalrecordRepository;

    public MedicalrecordService(MedicalrecordRepository medicalrecordRepository) {
        this.medicalrecordRepository = medicalrecordRepository;
    }

    // POST
    public Medicalrecord addMedicalRecord(Medicalrecord medicalrecord) {
        medicalrecordRepository.findAll().add(medicalrecord);
        return medicalrecord;
    }

    // PUT
    public Medicalrecord updateMedicalRecord(Medicalrecord updatedRecord) {

        var records = medicalrecordRepository.findAll();

        for (int i = 0; i < records.size(); i++) {

            Medicalrecord existing = records.get(i);

            if (existing.getFirstName().equalsIgnoreCase(updatedRecord.getFirstName())
                    && existing.getLastName().equalsIgnoreCase(updatedRecord.getLastName())) {

                records.set(i, updatedRecord);
                return updatedRecord;
            }
        }

        return null;
    }

    // DELETE
    public boolean deleteMedicalrecord(String firstName, String lastName) {

        return medicalrecordRepository.findAll()
                .removeIf(m ->
                        m.getFirstName().equalsIgnoreCase(firstName)
                                && m.getLastName().equalsIgnoreCase(lastName));
    }
}
