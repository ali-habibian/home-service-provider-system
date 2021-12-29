package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.entity.user.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProficientServiceTest {

    @Autowired
    private ProficientService proficientService;

    @Test
    void save() {
        Proficient proficient = new Proficient();
        proficient.setFirstName("First-Proficient-3");
        proficient.setLastName("Last-Proficient-3");
        proficient.setEmail("Email-Proficient-3@mail.com");
        proficient.setPassword("12345678asd");
        proficient.setProficientStatus(UserStatus.NEW);

        Proficient result = proficientService.save(proficient);
        assertNotNull(result);
    }

    @Test
    void loadByEmail() {
        Proficient proficient = proficientService.loadByEmail("Email-Proficient-2@mail.com");
        assertEquals(2L, proficient.getId());
    }
}