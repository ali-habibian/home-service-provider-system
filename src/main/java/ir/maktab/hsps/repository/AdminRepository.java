package ir.maktab.hsps.repository;

import ir.maktab.hsps.entity.user.Admin;
import ir.maktab.hsps.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
}
