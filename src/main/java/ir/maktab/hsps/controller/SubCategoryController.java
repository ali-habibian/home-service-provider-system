package ir.maktab.hsps.controller;

import ir.maktab.hsps.api.category.CategoryCreateResult;
import ir.maktab.hsps.api.category.sub_category.AddProficientToSubCatResult;
import ir.maktab.hsps.api.category.sub_category.RemoveProficientFromSubCatResult;
import ir.maktab.hsps.api.category.sub_category.SubCategoryCreateParam;
import ir.maktab.hsps.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subCategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @PostMapping
    public ResponseEntity<CategoryCreateResult> createSubCategory(@RequestBody SubCategoryCreateParam createParam) {
        CategoryCreateResult categoryCreateResult = subCategoryService.saveSubCategory(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryCreateResult);
    }

    @PutMapping("/{subCategoryId}/proficients/{proficientId}")
    public ResponseEntity<AddProficientToSubCatResult> addProficientToSubCat(
            @PathVariable long subCategoryId, @PathVariable long proficientId) {
        AddProficientToSubCatResult addProficientToSubCatResult = subCategoryService.addProficient(proficientId, subCategoryId);
        return ResponseEntity.ok(addProficientToSubCatResult);
    }

    @DeleteMapping("/{subCategoryId}/proficients/{proficientId}")
    public ResponseEntity<RemoveProficientFromSubCatResult> removeProficientFromSubCat(
            @PathVariable long subCategoryId, @PathVariable long proficientId) {
        RemoveProficientFromSubCatResult removeProficientFromSubCatResult = subCategoryService.removeProficient(proficientId, subCategoryId);
        return ResponseEntity.ok(removeProficientFromSubCatResult);
    }

}
