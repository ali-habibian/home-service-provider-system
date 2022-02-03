package ir.maktab.hsps.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import ir.maktab.hsps.api.user.UserChangePasswordParam;
import ir.maktab.hsps.api.user.UserChangePasswordResult;
import ir.maktab.hsps.api.user.customer.*;
import ir.maktab.hsps.entity.ConfirmationToken;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.User;
import ir.maktab.hsps.entity.user.UserStatus;
import ir.maktab.hsps.exception.EmailException;
import ir.maktab.hsps.exception.PasswordException;
import ir.maktab.hsps.repository.CustomerRepository;
import ir.maktab.hsps.security.email.EmailSender;
import ir.maktab.hsps.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ir.maktab.hsps.security.ApplicationUserRole.CUSTOMER;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final Utility utility;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailValidatorService emailValidatorService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public CustomerCreateResult saveCustomer(CustomerCreateParam createParam) {

        boolean isValidEmail = emailValidatorService.test(createParam.getEmail());

        if (!isValidEmail) {
            throw new EmailException(String.format("Email: %s, is not valid", createParam.getEmail()));
        }

        Customer loadByEmail = loadByEmail(createParam.getEmail());
        if (loadByEmail != null) {
            throw new EmailException("Another customer with this email already exists");
        }

        if (utility.passwordIsNotValid(createParam.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }

        Customer customer = createParam.convert2Customer();
        customer.setPassword(bCryptPasswordEncoder.encode(createParam.getPassword()));
        customer.setApplicationUserRole(CUSTOMER);
        customer.setCustomerStatus(UserStatus.NEW);
        customer.setCredit(0.0);

        Customer saveResult = customerRepository.save(customer);

        String token = utility.createRandomToken();
        ConfirmationToken confirmationToken = utility.createConfirmationToken(saveResult, token);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8080/customers/confirm?token=" + token;
        emailSender.send(createParam.getEmail(), utility.buildEmail(createParam.getFirstName(), link));

        return new CustomerCreateResult(saveResult.getId());
    }

    public CustomerUpdateResult update(CustomerUpdateParam updateParam) {
        Customer customer = updateParam.convert2Customer();

        Customer customerInDb = customerRepository.getById(customer.getId());
        if (!customerInDb.getPassword().equals(customer.getPassword())) {
            throw new PasswordException("Password is incorrect");
        }

        Customer loadByEmail = loadByEmail(customer.getEmail());
        if (loadByEmail != null && !Objects.equals(loadByEmail.getId(), customer.getId())) {
            throw new EmailException("Another customer with this email already exists");
        }

        customer.setApplicationUserRole(CUSTOMER);
        customer.setCustomerStatus(UserStatus.AWAITING_APPROVAL);
        Customer result = customerRepository.save(customer);
        return CustomerUpdateResult.builder()
                .id(result.getId())
                .success(true)
                .build();
    }

    @Transactional
    public UserChangePasswordResult changePassword(UserChangePasswordParam changePasswordParam) {
        long customerId = changePasswordParam.getUserId();
        String oldPass = changePasswordParam.getCurrentPassword();
        String newPass = changePasswordParam.getNewPassword();
        String confirmNewPass = changePasswordParam.getNewPasswordConfirm();

        if (!newPass.equals(confirmNewPass)) {
            throw new PasswordException("New password and confirm password doesn't match");
        }
        Customer customer = customerRepository.getById(customerId);
        if (!Objects.equals(customer.getPassword(), oldPass)) {
            throw new PasswordException("Old password doesn't match");
        }
        if (utility.passwordIsNotValid(newPass)) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        customer.setPassword(newPass);
        Customer updateResult = customerRepository.save(customer);
        return new UserChangePasswordResult(updateResult.getId(), updateResult.getPassword());
    }

//    @Transactional
//    public Customer increaseCredit(Long id, Double amount) { // TODO change to use DTO
//        Customer customer = loadById(id);
//        double newCredit = customer.getCredit() + amount;
//        customer.setCredit(newCredit);
//        return super.update(customer);
//    }

//    @Transactional
//    public Customer decreaseCredit(Long id, Double amount) { // TODO change to use DTO
//        Customer customer = loadById(id);
//        double credit = customer.getCredit();
//        if (credit < amount) {
//            throw new CreditException("Credit is not enough");
//        }
//        double newCredit = credit - amount;
//        customer.setCredit(newCredit);
//        return super.update(customer);
//    }

    public CustomerListResult loadAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        CustomerListResult customerListResult = new CustomerListResult();
        customerList.forEach((c) -> customerListResult.addCustomerModel(new CustomerModel().convertCustomer2Model(c)));
        return customerListResult;
    }

    public CustomerListResult loadAllCustomersByStatus(UserStatus status) {
        List<Customer> customerList = customerRepository.findAllByCustomerStatus(status);
        CustomerListResult customerListResult = new CustomerListResult();
        customerList.forEach((c) -> customerListResult.addCustomerModel(new CustomerModel().convertCustomer2Model(c)));
        return customerListResult;
    }

    public CustomerModel loadByIdReturnModel(long customerId) {
        Customer customer = customerRepository.getById(customerId);
        return new CustomerModel().convertCustomer2Model(customer);
    }

    @Transactional(readOnly = true)
    public Customer loadById(long customerId) {
        return customerRepository.getById(customerId);
    }

    private Customer loadByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token is expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        enableUser(confirmationToken.getUser().getEmail());

        return String.format("Email: %s confirmed", confirmationToken.getUser().getEmail());
    }

    private void enableUser(String email) {
        Customer user = customerRepository.findByEmail(email);
        user.setEnabled(true);
        user.setCustomerStatus(UserStatus.AWAITING_APPROVAL);
        customerRepository.save(user);
    }

    public CustomerUpdateResult confirmCustomerByAdmin(long userId) {
        Customer customer = customerRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
        customer.setCustomerStatus(UserStatus.CONFIRMED);
        customer.setLocked(false);
        Customer updateResult = customerRepository.save(customer);
        return new CustomerUpdateResult(updateResult.getId(), true);
    }

    public Iterable<Customer> findAll(BooleanExpression exp) {
        return customerRepository.findAll(exp);
    }
}
