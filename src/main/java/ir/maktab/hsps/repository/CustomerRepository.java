package ir.maktab.hsps.repository;

import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);

    List<Customer> findAllByCustomerStatus(UserStatus status);
}
