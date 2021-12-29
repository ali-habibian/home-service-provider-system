package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.category.MainCategory;
import ir.maktab.hsps.entity.category.SubCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubCategoryServiceTest {

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private MainCategoryService mainCategoryService;

    @Test
    void test_save() {
        MainCategory mainCategory = mainCategoryService.loadById(1L);
        System.out.println(mainCategory.getName());
        SubCategory subCategory = new SubCategory();
        subCategory.setName("Sub-Cat-4");
        subCategory.setMainCategory(mainCategory);

        SubCategory result = subCategoryService.save(subCategory);
        assertNotNull(result);
    }

    @Test
    void test_load_by_mainCategory_id() {
        List<SubCategory> subCategories = subCategoryService.loadByMainCategoryId(1);
        subCategories.forEach(System.out::println);
        assertEquals(4, subCategories.size());
    }
}