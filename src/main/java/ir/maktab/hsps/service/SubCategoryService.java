package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.category.SubCategory;
import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubCategoryService extends BaseService<SubCategory, Long> {
    private final SubCategoryRepository subCategoryRepository;
    private final ProficientService proficientService;

    @PostConstruct
    public void init() {
        setJpaRepository(subCategoryRepository);
    }

    public List<SubCategory> loadByMainCategoryId(long mainCatId) {
        return subCategoryRepository.findByMainCategoryId(mainCatId);
    }

    @Transactional
    public SubCategory addProficient(long subCategoryId, long proficientId) {
        SubCategory subCategory = subCategoryRepository.getById(subCategoryId);
        Proficient proficient = proficientService.loadById(proficientId);

        Set<Proficient> proficients = subCategory.getProficients();
        proficients.add(proficient);
        subCategory.setProficients(proficients);

        return super.update(subCategory);
    }

    @Transactional
    public SubCategory removeProficient(long subCategoryId, long proficientId) {
        SubCategory subCategory = subCategoryRepository.getById(subCategoryId);
        Proficient proficient = proficientService.loadById(proficientId);

        Set<Proficient> proficients = subCategory.getProficients();
        proficients.remove(proficient);
        subCategory.setProficients(proficients);

        return super.update(subCategory);
    }
}
