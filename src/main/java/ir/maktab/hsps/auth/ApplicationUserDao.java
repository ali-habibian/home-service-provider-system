package ir.maktab.hsps.auth;

import ir.maktab.hsps.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserDao{
    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
