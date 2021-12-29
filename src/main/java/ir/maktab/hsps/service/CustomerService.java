package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.UserStatus;
import ir.maktab.hsps.exception.CreditException;
import ir.maktab.hsps.exception.EmailException;
import ir.maktab.hsps.exception.PasswordException;
import ir.maktab.hsps.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CustomerService extends BaseService<Customer, Long> {
    private final CustomerRepository customerRepository;

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

        if (passwordIsNotValid(customer.getPassword())) {
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

        if (passwordIsNotValid(customer.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        customer.setCustomerStatus(UserStatus.AWAITING_APPROVAL);
        return super.update(customer);
    }

    public Customer increaseCredit(Long id, Double amount) {
        Customer customer = loadById(id);
        double newCredit = customer.getCredit() + amount;
        customer.setCredit(newCredit);
        return super.update(customer);
    }

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
