package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.entity.user.UserStatus;
import ir.maktab.hsps.exception.CreditException;
import ir.maktab.hsps.exception.EmailException;
import ir.maktab.hsps.exception.PasswordException;
import ir.maktab.hsps.repository.ProficientRepository;
import ir.maktab.hsps.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProficientService extends BaseService<Proficient, Long> {
    private final ProficientRepository proficientRepository;
    private final Utility utility;

    @PostConstruct
    public void init() {
        setJpaRepository(proficientRepository);
    }

    @Override
    public Proficient save(Proficient proficient) {
        Proficient loadByEmail = loadByEmail(proficient.getEmail());
        if (loadByEmail != null) {
            throw new EmailException("Another proficient with this email already exists");
        }

        if (utility.passwordIsNotValid(proficient.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        proficient.setProficientStatus(UserStatus.NEW);
        return super.save(proficient);
    }

    @Override
    public Proficient update(Proficient proficient) {
        Proficient loadByEmail = loadByEmail(proficient.getEmail());
        if (loadByEmail != null && !Objects.equals(loadByEmail.getId(), proficient.getId())) {
            throw new EmailException("Another proficient with this email already exists");
        }

        if (utility.passwordIsNotValid(proficient.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        proficient.setProficientStatus(UserStatus.AWAITING_APPROVAL);
        return super.update(proficient);
    }

    @Transactional
    public Proficient changePassword(long proficientId, String oldPass, String newPass) {
        Proficient proficient = proficientRepository.getById(proficientId);
        if (!Objects.equals(proficient.getPassword(), oldPass)) {
            throw new PasswordException("Old password doesn't match");
        }
        if (utility.passwordIsNotValid(newPass)) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        proficient.setPassword(newPass);
        return super.update(proficient);
    }

    @Transactional
    public Proficient increaseCredit(Long id, Double amount) {
        Proficient proficient = loadById(id);
        double newCredit = proficient.getCredit() + amount;
        proficient.setCredit(newCredit);
        return super.update(proficient);
    }

    public Proficient decreaseCredit(Long id, Double amount) {
        Proficient proficient = loadById(id);
        double credit = proficient.getCredit();
        if (credit < amount) {
            throw new CreditException("Credit is not enough");
        }
        double newCredit = credit - amount;
        proficient.setCredit(newCredit);
        return super.update(proficient);
    }

    public Proficient loadByEmail(String email) {
        return proficientRepository.findByEmail(email);
    }
}
