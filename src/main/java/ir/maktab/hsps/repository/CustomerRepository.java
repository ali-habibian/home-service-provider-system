package ir.maktab.hsps.repository;

import ir.maktab.hsps.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
