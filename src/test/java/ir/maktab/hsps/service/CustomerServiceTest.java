package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.User;
import ir.maktab.hsps.entity.user.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    void test_save_customer_isOk() {
        Customer customer = new Customer();
        customer.setFirstName("Ali");
        customer.setLastName("Habibian");
        customer.setEmail("ali.habibian@gmail.com");
        customer.setPassword("123");
        customer.setCustomerStatus(UserStatus.NEW);
        customer.setCredit(10000d);



        Customer result = customerService.saveCustomer(customer);
        assertNotNull(result);
    }

}