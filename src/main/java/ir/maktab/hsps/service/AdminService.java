package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.user.Admin;
import ir.maktab.hsps.exception.EmailException;
import ir.maktab.hsps.exception.PasswordException;
import ir.maktab.hsps.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AdminService extends BaseService<Admin, Long> {
    private final AdminRepository adminRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(adminRepository);
    }

    @Override
    public Admin save(Admin admin) {
        Admin loadByEmail = loadByEmail(admin.getEmail());
        if (loadByEmail != null) {
            throw new EmailException("Another admin with this email already exists");
        }

        if (!passwordIsValid(admin.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        return super.save(admin);
    }

    @Override
    public Admin update(Admin admin) {
        Admin loadByEmail = loadByEmail(admin.getEmail());
        if (loadByEmail != null && !Objects.equals(loadByEmail.getId(), admin.getId())) {
            throw new EmailException("Another admin with this email already exists");
        }

        if (!passwordIsValid(admin.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        return super.update(admin);
    }

    public Admin loadByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    private boolean passwordIsValid(String password) {
        if (password == null) {
            return false;
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z,A-Z]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
