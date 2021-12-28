package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer){ // Password validations in ui will be implemented
        return customerRepository.save(customer);
    }
}
