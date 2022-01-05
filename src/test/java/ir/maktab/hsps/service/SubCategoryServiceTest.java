package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.category.MainCategory;
import ir.maktab.hsps.entity.category.SubCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubCategoryServiceTest {

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private MainCategoryService mainCategoryService;

    @Autowired
    private ProficientService proficientService;

    @Test
    void test_save() {
        MainCategory mainCategory = mainCategoryService.loadById(2L);
        System.out.println(mainCategory.getName());
        SubCategory subCategory = new SubCategory();
        subCategory.setName("Sub-Cat-6");
        subCategory.setMainCategory(mainCategory);

        SubCategory result = subCategoryService.save(subCategory);
        assertNotNull(result);
    }

    @Test
    void test_add_proficient_isOk() {
        SubCategory subCategory = subCategoryService.addProficient(2, 3);
        assertTrue(subCategory.getProficients().contains(proficientService.loadById(3L)));
    }

    @Test
    void test_remove_proficient_isOk() {
        SubCategory subCategory = subCategoryService.removeProficient(2, 3);
        assertFalse(subCategory.getProficients().contains(proficientService.loadById(3L)));
    }

    @Test
    void test_load_by_mainCategory_id() {
        List<SubCategory> subCategories = subCategoryService.loadByMainCategoryId(1);
        subCategories.forEach(System.out::println);
        assertEquals(4, subCategories.size());
    }
}