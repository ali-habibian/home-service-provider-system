package ir.maktab.hsps.repository;

import ir.maktab.hsps.entity.category.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findByMainCategoryId(long mainCategoryId);
}
