package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.UserStatus;
import ir.maktab.hsps.exception.CreditException;
import ir.maktab.hsps.exception.EmailException;
import ir.maktab.hsps.exception.PasswordException;
import ir.maktab.hsps.repository.CustomerRepository;
import ir.maktab.hsps.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService extends BaseService<Customer, Long> {
    private final CustomerRepository customerRepository;
    private final Utility utility;

    @PostConstruct
    public void init() {
        setJpaRepository(customerRepository);
    }

    @Override
    public Customer save(Customer customer) {
        Customer loadByEmail = loadByEmail(customer.getEmail());
        if (loadByEmail != null) {
            throw new EmailException("Another customer with this email already exists");
        }

        if (utility.passwordIsNotValid(customer.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        customer.setCustomerStatus(UserStatus.NEW);
        return super.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        Customer loadByEmail = loadByEmail(customer.getEmail());
        if (loadByEmail != null && !Objects.equals(loadByEmail.getId(), customer.getId())) {
            throw new EmailException("Another customer with this email already exists");
        }

        if (utility.passwordIsNotValid(customer.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        customer.setCustomerStatus(UserStatus.AWAITING_APPROVAL);
        return super.update(customer);
    }

    @Transactional
    public Customer changePassword(long customerId, String oldPass, String newPass) {
        Customer customer = customerRepository.getById(customerId);
        if (!Objects.equals(customer.getPassword(), oldPass)) {
            throw new PasswordException("Old password doesn't match");
        }
        if (utility.passwordIsNotValid(newPass)) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        customer.setPassword(newPass);
        return super.update(customer);
    }

    @Transactional
    public Customer increaseCredit(Long id, Double amount) {
        Customer customer = loadById(id);
        double newCredit = customer.getCredit() + amount;
        customer.setCredit(newCredit);
        return super.update(customer);
    }

    @Transactional
    public Customer decreaseCredit(Long id, Double amount) {
        Customer customer = loadById(id);
        double credit = customer.getCredit();
        if (credit < amount) {
            throw new CreditException("Credit is not enough");
        }
        double newCredit = credit - amount;
        customer.setCredit(newCredit);
        return super.update(customer);
    }

    public Customer loadByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
