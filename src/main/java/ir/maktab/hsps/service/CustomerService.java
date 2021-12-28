package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CustomerService extends BaseService<Customer, Long> {
    private final CustomerRepository customerRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(customerRepository);
    }
}
