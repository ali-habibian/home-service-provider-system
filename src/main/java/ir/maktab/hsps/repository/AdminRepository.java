package ir.maktab.hsps.repository;

import ir.maktab.hsps.entity.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
