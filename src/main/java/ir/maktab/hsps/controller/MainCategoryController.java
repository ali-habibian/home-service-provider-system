package ir.maktab.hsps.controller;

import ir.maktab.hsps.api.category.CategoryCreateResult;
import ir.maktab.hsps.api.category.CategoryUpdateResult;
import ir.maktab.hsps.api.category.main_category.MainCategoryCreateParam;
import ir.maktab.hsps.api.category.main_category.MainCategoryListResult;
import ir.maktab.hsps.api.category.main_category.MainCategoryModel;
import ir.maktab.hsps.api.category.main_category.MainCategoryUpdateParam;
import ir.maktab.hsps.service.MainCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mainCategories")
public class MainCategoryController {

    private final MainCategoryService mainCategoryService;

    @PostMapping
    public ResponseEntity<CategoryCreateResult> createMainCategory(@RequestBody MainCategoryCreateParam createParam) {
        CategoryCreateResult categoryCreateResult = mainCategoryService.saveMainCategory(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryCreateResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryUpdateResult> updateMainCategory(@RequestBody MainCategoryUpdateParam updateParam, @PathVariable Long id) {
        updateParam.setId(id);
        CategoryUpdateResult categoryUpdateResult = mainCategoryService.updateMainCategory(updateParam);
        return ResponseEntity.ok(categoryUpdateResult);
    }

    @GetMapping
    public ResponseEntity<MainCategoryListResult> loadAll() {
        MainCategoryListResult mainCategoryListResult = mainCategoryService.loadAll();
        return ResponseEntity.ok(mainCategoryListResult);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<MainCategoryModel> loadById(@PathVariable Long id) {
        MainCategoryModel mainCategoryModel = mainCategoryService.loadByIdReturnModel(id);
        return ResponseEntity.ok(mainCategoryModel);
    }
}
