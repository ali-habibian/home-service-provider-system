package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.Address;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    void test_save_customer_isOk() {
        Address address = Address.builder()
                .province("Province-5")
                .city("City-5")
                .street("Street-5")
                .alley("Alley-5")
                .plaque("5")
                .build();

        Customer customer = new Customer();
        customer.setFirstName("First-Customer-1");
        customer.setLastName("Last-Customer-1");
        customer.setEmail("Email-Customer-1@mail.com");
        customer.setPassword("12345678asd");
        customer.setCustomerStatus(UserStatus.NEW);
        customer.setCredit(10000d);
        customer.setAddress(address);

        Customer result = customerService.save(customer);
        assertNotNull(result);
    }

}