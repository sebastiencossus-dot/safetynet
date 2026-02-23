import com.example.model.Firestation;
import com.example.model.Person;
import com.example.repository.FirestationRepository;
import com.example.repository.MedicalrecordRepository;
import com.example.repository.PersonRepository;
import com.example.service.PersonService;

import org.junit.jupiter.api.Test; // ✅ IMPORTANT
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals; // ✅ JUnit 5
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalrecordRepository medicalrecordRepository;

    @Mock
    private FirestationRepository firestationRepository; // ✅ MANQUANT

    @InjectMocks
    private PersonService personService;

    @Test
    void shouldReturnEmailsByCity() {

        // GIVEN
        Person person = new Person();
        person.setFirstName("Sebastien");
        person.setLastName("Cossus");
        person.setAddress("55 av");
        person.setCity("Aix");
        person.setZip("13090");
        person.setPhone("0606060606");
        person.setEmail("testreussi@hotmail.fr");

        when(personRepository.findAll())
                .thenReturn(List.of(person));

        // WHEN
        List<String> result =
                personService.getEmailsByCity("Aix");

        // THEN
        assertEquals(1, result.size());
        assertEquals("testreussi@hotmail.fr", result.get(0));
    }

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
        firestation.getStation("8")

        when(personRepository.findAll())
                .thenReturn(List.of(person));

        // WHEN
        List<String> result =
                personService.getAdressByCity("Aix")

        // THEN
        assertEquals(1, result.size());
        assertEquals("testreussi@hotmail.fr", result.get(0));
    }
}