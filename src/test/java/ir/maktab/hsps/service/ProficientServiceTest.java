package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.category.SubCategory;
import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.entity.user.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProficientServiceTest {

    @Autowired
    private ProficientService proficientService;

    @Autowired
    SubCategoryService subCategoryService;

    @Test
    void save() {
        Proficient proficient = new Proficient();
        proficient.setFirstName("First-Proficient-4");
        proficient.setLastName("Last-Proficient-4");
        proficient.setEmail("Email-Proficient-4@mail.com");
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
    void loadByEmail() {
        Proficient proficient = proficientService.loadByEmail("Email-Proficient-2@mail.com");
        assertEquals(2L, proficient.getId());
    }
}