package ir.maktab.hsps.service;

import ir.maktab.hsps.api.user.UserChangePasswordParam;
import ir.maktab.hsps.api.user.UserChangePasswordResult;
import ir.maktab.hsps.api.user.customer.CustomerListResult;
import ir.maktab.hsps.api.user.customer.CustomerModel;
import ir.maktab.hsps.api.user.proficient.ProficientCreateParam;
import ir.maktab.hsps.api.user.proficient.ProficientListResult;
import ir.maktab.hsps.api.user.proficient.ProficientModel;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.entity.user.UserRole;
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
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProficientService {
    private final ProficientRepository proficientRepository;
    private final Utility utility;

    public ProficientModel save(ProficientCreateParam createParam) throws IOException {
        Proficient proficient = createParam.convert2Proficient();
        Proficient loadByEmail = loadByEmail(createParam.getEmail());
        if (loadByEmail != null) {
            throw new EmailException("Another proficient with this email already exists");
        }

        if (utility.passwordIsNotValid(proficient.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        proficient.setProficientStatus(UserStatus.NEW);
        proficient.setUserRole(UserRole.PROFICIENT);
        proficient.setCredit(0.0);
        Proficient saveResult = proficientRepository.save(proficient);
        return new ProficientModel().convertProficient2Model(saveResult);
    }

    public Proficient update(Proficient proficient) {
        Proficient loadByEmail = loadByEmail(proficient.getEmail());
        if (loadByEmail != null && !Objects.equals(loadByEmail.getId(), proficient.getId())) {
            throw new EmailException("Another proficient with this email already exists");
        }

        if (utility.passwordIsNotValid(proficient.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        proficient.setProficientStatus(UserStatus.AWAITING_APPROVAL);
        return proficientRepository.save(proficient);
    }

    @Transactional
    public UserChangePasswordResult changePassword(UserChangePasswordParam changePasswordParam) {
        long proficientId = changePasswordParam.getUserId();
        String oldPass = changePasswordParam.getCurrentPassword();
        String newPass = changePasswordParam.getNewPassword();
        String confirmNewPass = changePasswordParam.getNewPasswordConfirm();

        if (!newPass.equals(confirmNewPass)) {
            throw new PasswordException("New password and confirm password doesn't match");
        }
        Proficient proficient = proficientRepository.getById(proficientId);
        if (!Objects.equals(proficient.getPassword(), oldPass)) {
            throw new PasswordException("Old password doesn't match");
        }
        if (utility.passwordIsNotValid(newPass)) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        proficient.setPassword(newPass);
        Proficient result = proficientRepository.save(proficient);
        return new UserChangePasswordResult(result.getId(), result.getPassword());
    }

//    @Transactional
//    public Proficient increaseCredit(Long id, Double amount) {
//        Proficient proficient = loadById(id);
//        double newCredit = proficient.getCredit() + amount;
//        proficient.setCredit(newCredit);
//        return super.update(proficient);
//    }
//
//    public Proficient decreaseCredit(Long id, Double amount) {
//        Proficient proficient = loadById(id);
//        double credit = proficient.getCredit();
//        if (credit < amount) {
//            throw new CreditException("Credit is not enough");
//        }
//        double newCredit = credit - amount;
//        proficient.setCredit(newCredit);
//        return super.update(proficient);
//    }

    public ProficientListResult loadAllProficients() {
        List<Proficient> proficientList = proficientRepository.findAll();
        ProficientListResult proficientListResult = new ProficientListResult();
        proficientList.forEach((p) -> proficientListResult.addProficientModel(
                new ProficientModel().convertProficient2Model(p)));
        return proficientListResult;
    }

    public ProficientListResult loadAllProficientsByStatus(UserStatus status) {
        List<Proficient> proficientList = proficientRepository.findAllByProficientStatus(status);
        ProficientListResult proficientListResult = new ProficientListResult();
        proficientList.forEach((p) -> proficientListResult.addProficientModel(
                new ProficientModel().convertProficient2Model(p)));
        return proficientListResult;
    }

    public ProficientListResult loadAllProficientsBySubCategoryId(long subCategoryId) {
        List<Proficient> proficientList = proficientRepository.findAllBySubCategoriesId(subCategoryId);
        ProficientListResult proficientListResult = new ProficientListResult();
        proficientList.forEach((p) -> proficientListResult.addProficientModel(
                new ProficientModel().convertProficient2Model(p)));
        return proficientListResult;
    }

    public Proficient loadByEmail(String email) {
        return proficientRepository.findByEmail(email);
    }

    public Proficient loadById(long proficientId) {
        return null;
    }
}
