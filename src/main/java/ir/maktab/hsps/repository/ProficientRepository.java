package ir.maktab.hsps.repository;

import ir.maktab.hsps.entity.user.Proficient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProficientRepository extends JpaRepository<Proficient, Long> {
    Proficient findByEmail(String email);
}
