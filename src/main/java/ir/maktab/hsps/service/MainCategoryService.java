package ir.maktab.hsps.service;

import ir.maktab.hsps.api.category.CategoryCreateResult;
import ir.maktab.hsps.api.category.CategoryUpdateResult;
import ir.maktab.hsps.api.category.main_category.MainCategoryCreateParam;
import ir.maktab.hsps.api.category.main_category.MainCategoryListResult;
import ir.maktab.hsps.api.category.main_category.MainCategoryModel;
import ir.maktab.hsps.api.category.main_category.MainCategoryUpdateParam;
import ir.maktab.hsps.entity.category.MainCategory;
import ir.maktab.hsps.repository.MainCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainCategoryService {
    private final MainCategoryRepository mainCategoryRepository;

    @Transactional
    public CategoryCreateResult saveMainCategory(MainCategoryCreateParam createParam) {
        MainCategory mainCategory = new MainCategory();
        mainCategory.setName(createParam.getName());

        MainCategory saveResult = mainCategoryRepository.save(mainCategory);
        return new CategoryCreateResult(saveResult.getId());
    }

    @Transactional
    public CategoryUpdateResult updateMainCategory(MainCategoryUpdateParam updateParam) {
        MainCategory mainCategory = mainCategoryRepository.getById(updateParam.getId());
        mainCategory.setName(updateParam.getName());

        MainCategory saveResult = mainCategoryRepository.save(mainCategory);
        return new CategoryUpdateResult(saveResult.getId(), true);
    }

    @Transactional(readOnly = true)
    public MainCategory loadById(long id) {
        return mainCategoryRepository.getById(id);
    }

    @Transactional(readOnly = true)
    public MainCategoryModel loadByIdReturnModel(long id) {
        MainCategory mainCategory = mainCategoryRepository.getById(id);
        return new MainCategoryModel().convertMainCat2Model(mainCategory);
    }

    @Transactional(readOnly = true)
    public MainCategoryListResult loadAll() {
        List<MainCategory> mainCategories = mainCategoryRepository.findAll();
        MainCategoryListResult mainCategoryListResult = new MainCategoryListResult();
        mainCategories.forEach((c) -> mainCategoryListResult.addMainCategoryModel(new MainCategoryModel().convertMainCat2Model(c)));
        return mainCategoryListResult;
    }
}
