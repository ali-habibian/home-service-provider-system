package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.category.MainCategory;
import ir.maktab.hsps.repository.MainCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class MainCategoryService extends BaseService<MainCategory, Long> {
    private final MainCategoryRepository mainCategoryRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(mainCategoryRepository);
    }
}
