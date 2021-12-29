package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.category.MainCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainCategoryServiceTest {

    @Autowired
    private MainCategoryService mainCategoryService;

    @Test
    void test_save(){
        MainCategory mainCategory = new MainCategory();
        mainCategory.setName("MainCategory-2");

        MainCategory result = mainCategoryService.save(mainCategory);
        assertNotNull(result);
    }

    @Test
    void load_by_id(){
        long id = 1;
        MainCategory mainCategory = mainCategoryService.loadById(id);
        mainCategory.getSubCategorySet().forEach(System.out::println);
        assertNotNull(mainCategory);
    }
}