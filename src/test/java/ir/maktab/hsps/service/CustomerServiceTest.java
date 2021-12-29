package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.Address;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.UserStatus;
import ir.maktab.hsps.exception.CreditException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void test_increase_credit() {
        Customer customer = customerService.increaseCredit(1L, 5000.0);
        assertEquals(15000.0, customer.getCredit());
    }

    @Test
    void test_decrease_credit_isOk() {
        Customer customer = customerService.decreaseCredit(1L, 5000.0);
        assertEquals(10000.0, customer.getCredit());
    }

    @Test
    void test_decrease_credit_credit_not_enough() {
        assertThrows(CreditException.class, () -> customerService.decreaseCredit(1L, 15000.0));
    }

}