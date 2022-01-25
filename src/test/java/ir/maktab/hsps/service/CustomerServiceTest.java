package ir.maktab.hsps.service;

import ir.maktab.hsps.api.user.UserChangePasswordParam;
import ir.maktab.hsps.api.user.UserChangePasswordResult;
import ir.maktab.hsps.api.user.customer.CustomerCreateParam;
import ir.maktab.hsps.api.user.customer.CustomerCreateResult;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.UserRole;
import ir.maktab.hsps.exception.CreditException;
import ir.maktab.hsps.exception.PasswordException;
import ir.maktab.hsps.security.ApplicationUserRole;
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
        CustomerCreateParam createParam = CustomerCreateParam.builder()
                .firstName("First-Customer-9")
                .lastName("Last-Customer-9")
                .email("Email-Customer-9@mail.com")
                .password("12345678asd")
//                .userRole(ApplicationUserRole.CUSTOMER)
                .build();

        CustomerCreateResult result = customerService.saveCustomer(createParam);
        assertEquals(20, result.getCustomerId());
    }

    @Test
    void test_change_password() {
        UserChangePasswordParam changePasswordParam = UserChangePasswordParam.builder()
                .userId(1L)
                .currentPassword("456asd78")
                .newPassword("12345678asd")
                .newPasswordConfirm("12345678asd")
                .build();

        UserChangePasswordResult result = customerService.changePassword(changePasswordParam);
        assertEquals("12345678asd", result.getNewPassword());
    }

    @Test
    void test_change_password_with_wrong_old_pass() {
        UserChangePasswordParam changePasswordParam = UserChangePasswordParam.builder()
                .userId(1L)
                .currentPassword("456asd78")
                .newPassword("12345678asd")
                .newPasswordConfirm("12345678asd")
                .build();

        assertThrows(PasswordException.class, () ->
                customerService.changePassword(changePasswordParam)
        );
    }

    @Test
    void test_change_password_with_invalid_new_pass() {
        UserChangePasswordParam changePasswordParam = UserChangePasswordParam.builder()
                .userId(1L)
                .currentPassword("12345678asd")
                .newPassword("123")
                .newPasswordConfirm("123")
                .build();

        assertThrows(PasswordException.class, () ->
                customerService.changePassword(changePasswordParam)
        );
    }

    @Test
    void test_change_password_with_not_confirmed_new_pass() {
        UserChangePasswordParam changePasswordParam = UserChangePasswordParam.builder()
                .userId(1L)
                .currentPassword("12345678asd")
                .newPassword("123asd456")
                .newPasswordConfirm("123fgh456")
                .build();

        assertThrows(PasswordException.class, () ->
                customerService.changePassword(changePasswordParam)
        );
    }
}