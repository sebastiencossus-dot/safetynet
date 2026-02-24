import com.example.model.Firestation;
import com.example.model.Person;
import com.example.repository.FirestationRepository;
import com.example.repository.MedicalrecordRepository;
import com.example.repository.PersonRepository;
import com.example.service.FirestationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalrecordRepository medicalrecordRepository;

    @Mock
    private FirestationRepository firestationRepository; // ✅ MANQUANT

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

}
