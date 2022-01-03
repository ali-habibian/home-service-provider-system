package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.user.Admin;
import ir.maktab.hsps.exception.EmailException;
import ir.maktab.hsps.exception.PasswordException;
import ir.maktab.hsps.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (passwordIsNotValid(admin.getPassword())) {
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

        if (passwordIsNotValid(admin.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        return super.update(admin);
    }

    @Transactional
    public Admin changePassword(long adminId, String oldPass, String newPass) {
        Admin admin = adminRepository.getById(adminId);
        if (!Objects.equals(admin.getPassword(), oldPass)) {
            throw new PasswordException("Old password doesn't match");
        }
        if (passwordIsNotValid(newPass)) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        admin.setPassword(newPass);
        return super.update(admin);
    }

    public Admin loadByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    private boolean passwordIsNotValid(String password) {
        if (password == null) {
            return true;
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z,A-Z]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return !matcher.matches();
    }
}
