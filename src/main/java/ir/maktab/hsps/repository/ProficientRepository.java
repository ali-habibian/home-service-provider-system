package ir.maktab.hsps.repository;

import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.entity.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProficientRepository extends JpaRepository<Proficient, Long> {
    Proficient findByEmail(String email);

    List<Proficient> findAllByProficientStatus(UserStatus status);

    List<Proficient> findAllBySubCategoriesId(long subCategoryId);
}
