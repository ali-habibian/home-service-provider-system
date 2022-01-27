package ir.maktab.hsps.security.auth;

import java.util.Optional;

public interface ApplicationUserDao{
    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
