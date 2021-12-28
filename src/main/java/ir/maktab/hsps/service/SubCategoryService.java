package ir.maktab.hsps.service;

import ir.maktab.hsps.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;


}
