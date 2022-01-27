package ir.maktab.hsps.service;

import ir.maktab.hsps.security.auth.ApplicationUser;
import ir.maktab.hsps.security.auth.ApplicationUserDao;
import ir.maktab.hsps.entity.user.User;
import ir.maktab.hsps.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements ApplicationUserDao {
    private final UserRepository userRepository;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        User user = userRepository.findByEmail(username);

        ApplicationUser applicationUser = new ApplicationUser(
                user.getId(),
                user.getApplicationUserRole().getGrantedAuthorities(),
                user.getPassword(),
                user.getEmail(),
                true,
                user.isAccountNonLocked(),
                true,
                user.isEnabled()
        );
        return Optional.of(applicationUser);
    }

}
