package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.category.SubCategory;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.entity.user.UserStatus;
import ir.maktab.hsps.exception.PasswordException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProficientServiceTest {

    @Autowired
    private ProficientService proficientService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Test
    void save() {
        Proficient proficient = new Proficient();
        proficient.setFirstName("First-Proficient-6");
        proficient.setLastName("Last-Proficient-6");
        proficient.setEmail("Email-Proficient-6@mail.com");
        proficient.setPassword("12345678asd");
        proficient.setProficientStatus(UserStatus.NEW);

        SubCategory subCategory = subCategoryService.loadById(1L);
        SubCategory subCategory2 = subCategoryService.loadById(2L);

        Set<SubCategory> subCategorySet = new HashSet<>();
        subCategorySet.add(subCategory);
        subCategorySet.add(subCategory2);

        proficient.setSubCategories(subCategorySet);

        Proficient result = proficientService.save(proficient);
        assertNotNull(result);
    }

    @Test
    void test_change_password() {
        Proficient proficient = proficientService.changePassword(1, "12345678asd", "456asd78");
        assertEquals("456asd78", proficient.getPassword());
    }

    @Test
    void test_change_password_with_wrong_old_pass() {
        assertThrows(PasswordException.class, () ->
                proficientService.changePassword(1, "123asd45", "456asd78")
        );
    }

    @Test
    void test_change_password_with_invalid_new_pass() {
        assertThrows(PasswordException.class, () ->
                proficientService.changePassword(1, "456asd78", "123")
        );
    }

    @Test
    void loadByEmail() {
        Proficient proficient = proficientService.loadByEmail("Email-Proficient-2@mail.com");
        assertEquals(2L, proficient.getId());
    }
}