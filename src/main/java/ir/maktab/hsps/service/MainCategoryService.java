package ir.maktab.hsps.service;

import ir.maktab.hsps.repository.MainCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainCategoryService {
    private final MainCategoryRepository mainCategoryRepository;


}
