package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.Address;
import ir.maktab.hsps.entity.user.Admin;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.UserStatus;
import ir.maktab.hsps.exception.CreditException;
import ir.maktab.hsps.exception.PasswordException;
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
                .province("Province-6")
                .city("City-6")
                .street("Street-6")
                .alley("Alley-6")
                .plaque("6")
                .build();

        Customer customer = new Customer();
        customer.setFirstName("First-Customer-2");
        customer.setLastName("Last-Customer-2");
        customer.setEmail("Email-Customer-2@mail.com");
        customer.setPassword("12345678asd");
//        customer.setCustomerStatus(UserStatus.NEW);
        customer.setCredit(10000d);
        customer.setAddress(address);

        Customer result = customerService.save(customer);
        assertNotNull(result);
    }

    @Test
    void test_change_password() {
        Customer customer = customerService.changePassword(1, "12345678asd", "456asd78");
        assertEquals("456asd78", customer.getPassword());
    }

    @Test
    void test_change_password_with_wrong_old_pass() {
        assertThrows(PasswordException.class, () ->
                customerService.changePassword(1, "123asd45", "456asd78")
        );
    }

    @Test
    void test_change_password_with_invalid_new_pass() {
        assertThrows(PasswordException.class, () ->
                customerService.changePassword(1, "456asd78", "123")
        );
    }

    @Test
    void test_load_by_id(){
        Customer customer = customerService.loadById(1L);
        System.out.println("customer.getAddress().getStreet() = " + customer.getAddress().getStreet());
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