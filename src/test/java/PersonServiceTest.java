import com.example.model.Firestation;
import com.example.model.Medicalrecord;
import com.example.model.Person;
import com.example.repository.FirestationRepository;
import com.example.repository.MedicalrecordRepository;
import com.example.repository.PersonRepository;
import com.example.service.DTO.ChildAlertDTO;
import com.example.service.DTO.PersonInfoDTO;
import com.example.service.FirestationService;
import com.example.service.PersonService;

import org.junit.jupiter.api.Test; // ✅ IMPORTANT
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.service.FirestationService.calculateAge;
import static org.junit.jupiter.api.Assertions.assertEquals; // ✅ JUnit 5
import static org.junit.jupiter.api.Assertions.assertTrue;
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


        List<String> result =
                personService.getEmailsByCity("Aix");


        assertEquals(1, result.size());
        assertEquals("testreussi@hotmail.fr", result.get(0));
    }

    @Test
    void shouldReturnPersonInfo() {

        // GIVEN
        Person person = new Person();
        person.setFirstName("Sebastien");
        person.setLastName("Cossus");
        person.setAddress("55 av");
        person.setCity("Aix");
        person.setZip("13090");
        person.setPhone("0606060606");
        person.setEmail("testreussi@hotmail.fr");

        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName("Sebastien");
        medicalrecord.setLastName("Cossus");
        medicalrecord.setBirthdate("07/19/1974");
        medicalrecord.setMedications(List.of("doliprane:500mg", "tramadol:100mg"));
        medicalrecord.setAllergies(List.of("pollen", "arachide"));


        when(personRepository.findAll())
                .thenReturn(List.of(person));
        when(medicalrecordRepository.findAll())
                .thenReturn(List.of(medicalrecord));

        List<PersonInfoDTO> result =
                personService.getPersonInfo("Sebastien", "Cossus");

        // THEN
        assertEquals(1, result.size());

        PersonInfoDTO dto = result.get(0);

        assertEquals("Sebastien", dto.firstName());
        assertEquals("Cossus", dto.lastName());
        assertEquals("55 av", dto.address());
        assertEquals("testreussi@hotmail.fr", dto.email());
        assertTrue(dto.age() > 40);

        assertEquals(2, dto.medications().size());
        assertEquals(2, dto.allergies().size());

        assertTrue(dto.allergies().contains("pollen"));
    }

    @Test
    void shouldReturnChildWithFoyerMembers() {

        // GIVEN

        // Enfant
        Person child = new Person();
        child.setFirstName("Dylan");
        child.setLastName("Cossus");
        child.setAddress("55 av");
        child.setCity("Aix");

        // Adulte du foyer
        Person adult = new Person();
        adult.setFirstName("Sebastien");
        adult.setLastName("Cossus");
        adult.setAddress("55 av");
        adult.setCity("Aix");

        // Medical record enfant (<18 ans)
        Medicalrecord childRecord = new Medicalrecord();
        childRecord.setFirstName("Dylan");
        childRecord.setLastName("Cossus");
        childRecord.setBirthdate("01/01/2015"); // enfant

        // Medical record adulte (>18 ans)
        Medicalrecord adultRecord = new Medicalrecord();
        adultRecord.setFirstName("Sebastien");
        adultRecord.setLastName("Cossus");
        adultRecord.setBirthdate("07/19/1974");

        when(personRepository.findAll())
                .thenReturn(List.of(child, adult));

        when(medicalrecordRepository.findAll())
                .thenReturn(List.of(childRecord, adultRecord));

        // WHEN
        List<ChildAlertDTO> result =
                personService.getChildByAddress("55 av");

        // THEN

        // 1 seul enfant retourné
        assertEquals(1, result.size());

        ChildAlertDTO dto = result.get(0);

        assertEquals("Dylan", dto.firstName());
        assertEquals("Cossus", dto.lastName());

        // Vérifie que le foyer contient l’adulte
        assertEquals(1, dto.foyer().size());
        assertTrue(dto.foyer().contains("Sebastien Cossus"));
    }


}