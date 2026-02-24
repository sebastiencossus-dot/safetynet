import com.example.model.Firestation;
import com.example.model.Medicalrecord;
import com.example.model.Person;
import com.example.repository.FirestationRepository;
import com.example.repository.MedicalrecordRepository;
import com.example.repository.PersonRepository;
import com.example.service.DTO.FoyerByStationDTO;
import com.example.service.DTO.ResidentsDTO;
import com.example.service.DTO.StationAdressDTO;
import com.example.service.FirestationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalrecordRepository medicalrecordRepository;

    @Mock
    private FirestationRepository firestationRepository;
    @InjectMocks
    private FirestationService firestationService;


    @Test
    void shouldReturnPhoneByStation() {

        // GIVEN
        Person person = new Person();
        person.setFirstName("Sebastien");
        person.setLastName("Cossus");
        person.setAddress("55 av");
        person.setCity("Aix");
        person.setZip("13090");
        person.setPhone("0606060606");
        person.setEmail("testreussi@hotmail.fr");

        Firestation firestation = new Firestation();
        firestation.setAddress("55 av");
        firestation.setStation("8");

        when(personRepository.findAll())
                .thenReturn(List.of(person));
        when(firestationRepository.findAll())
                .thenReturn(List.of(firestation));




        firestationService.getPhonesByStation("8");



        assertEquals("0606060606", person.getPhone());
    }


    @Test
    void shouldReturnStationAdressDTO() throws Exception {
        // ===== GIVEN =====
        String address = "55 av";

        Firestation firestation = new Firestation();
        firestation.setAddress(address);
        firestation.setStation("8");

        Person person = new Person();
        person.setFirstName("Sebastien");
        person.setLastName("Cossus");
        person.setAddress(address);
        person.setPhone("0606060606");

        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName("Sebastien");
        medicalrecord.setLastName("Cossus");
        medicalrecord.setBirthdate("07/19/2010");
        medicalrecord.setMedications(List.of("doliprane:500mg", "tramadol:100mg"));
        medicalrecord.setAllergies(List.of("pollen", "arachide"));

        when(firestationRepository.findAll()).thenReturn(List.of(firestation));
        when(personRepository.findAll()).thenReturn(List.of(person));
        when(medicalrecordRepository.findAll()).thenReturn(List.of(medicalrecord));

        // ===== WHEN =====
        StationAdressDTO result = firestationService.getStationForAddress(address);

        // ===== THEN =====
        assertEquals("8", result.station());
        assertEquals(1, result.residents().size());

        ResidentsDTO resident = result.residents().get(0);
        assertEquals("Sebastien", resident.firstName());
        assertEquals("Cossus", resident.lastName());
        assertEquals("0606060606", resident.phone());
        assertTrue(resident.age() < 18); // Vérifie qu'il est mineur
        assertNotNull(resident.medications());
        assertNotNull(resident.allergies());
    }

    @Test
    void shouldThrowExceptionWhenNoStation() {
        String address = "Unknown av";

        when(firestationRepository.findAll()).thenReturn(List.of());

        Exception exception = assertThrows(Exception.class, () -> {
            firestationService.getStationForAddress(address);
        });

        assertEquals("Pas de caserne a cette adresse", exception.getMessage());
    }

    @Test
    void shouldReturnforFlood() {
        // ===== GIVEN =====

        Firestation firestation = new Firestation();
        firestation.setAddress("55 av");
        firestation.setStation("8");

        Firestation firestation2 = new Firestation();
        firestation2.setAddress("12 bd");
        firestation2.setStation("7");

        Person person = new Person();
        person.setFirstName("Sebastien");
        person.setLastName("Cossus");
        person.setAddress("55 av");
        person.setPhone("0606060606");

        Person person2 = new Person();
        person2.setFirstName("Dylan");
        person2.setLastName("Cossus");
        person2.setAddress("55 av");
        person2.setPhone("0606060616");

        Person person3 = new Person();
        person3.setFirstName("Anne");
        person3.setLastName("dufour");
        person3.setAddress("12 bd");
        person3.setPhone("0202020202");

        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName("Sebastien");
        medicalrecord.setLastName("Cossus");
        medicalrecord.setBirthdate("07/19/2010");
        medicalrecord.setMedications(List.of("doliprane:500mg", "tramadol:100mg"));
        medicalrecord.setAllergies(List.of("pollen", "arachide"));

        Medicalrecord medicalrecord2 = new Medicalrecord();
        medicalrecord2.setFirstName("Dylan");
        medicalrecord2.setLastName("Cossus");
        medicalrecord2.setBirthdate("07/19/2020");
        medicalrecord2.setMedications(List.of("doliprane:500mg"));
        medicalrecord2.setAllergies(List.of("pollen"));

        Medicalrecord medicalrecord3 = new Medicalrecord();
        medicalrecord3.setFirstName("Anne");
        medicalrecord3.setLastName("Dufour");
        medicalrecord3.setBirthdate("07/19/2000");
        medicalrecord3.setMedications(List.of("doliprane:500mg"));
        medicalrecord3.setAllergies(List.of("pollen"));

        when(firestationRepository.findAll()).thenReturn(List.of(firestation, firestation2));
        when(personRepository.findAll()).thenReturn(List.of(person, person2, person3));
        when(medicalrecordRepository.findAll()).thenReturn(List.of(medicalrecord, medicalrecord2, medicalrecord3));

        List<FoyerByStationDTO> result =
                firestationService.getFloodByStations(List.of("8", "7"));
        System.out.println(result);
        assertEquals(2, result.size());

        FoyerByStationDTO foyer1 = result.stream()
                .filter(f -> f.address().equals("55 av"))
                .findFirst()
                .orElse(null);

        assertNotNull(foyer1);
        assertEquals(2, foyer1.Members().size());
        assertEquals("Sebastien", foyer1.Members().get(0).firstName());

        // Vérifie adresse 12 bd
        FoyerByStationDTO foyer2 = result.stream()
                .filter(f -> f.address().equals("12 bd"))
                .findFirst()
                .orElse(null);

        assertNotNull(foyer2);
        assertEquals(1, foyer2.Members().size());
        assertEquals("dufour", foyer2.Members().get(0).lastName());

    }
}
