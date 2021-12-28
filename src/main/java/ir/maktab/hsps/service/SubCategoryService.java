package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.category.SubCategory;
import ir.maktab.hsps.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class SubCategoryService extends BaseService<SubCategory, Long> {
    private final SubCategoryRepository subCategoryRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(subCategoryRepository);
    }
}
