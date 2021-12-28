package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.user.Admin;
import ir.maktab.hsps.exception.EmailException;
import ir.maktab.hsps.exception.PasswordException;
import ir.maktab.hsps.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    void test_save_isOk() {
        Admin admin = new Admin();
        admin.setFirstName("First-3");
        admin.setLastName("Last-3");
        admin.setEmail("Email-3@mail.com");
        admin.setPassword("123asd45");

        Admin result = adminService.save(admin);
        assertNotNull(result);
    }

    @Test
    void test_save_with_duplicate_email() {
        Admin admin = new Admin();
        admin.setFirstName("First-1");
        admin.setLastName("Last-1");
        admin.setEmail("Email-1@mail.com");
        admin.setPassword("123asd45");

        assertThrows(EmailException.class, () -> adminService.save(admin));
    }

    @Test
    void test_save_with_no_letter_pass() {
        Admin admin = new Admin();
        admin.setFirstName("First-4");
        admin.setLastName("Last-4");
        admin.setEmail("Email-4@mail.com");
        admin.setPassword("12312345");

        assertThrows(PasswordException.class, () -> adminService.save(admin));
    }

    @Test
    void test_save_with_no_number_pass() {
        Admin admin = new Admin();
        admin.setFirstName("First-4");
        admin.setLastName("Last-4");
        admin.setEmail("Email-4@mail.com");
        admin.setPassword("aaaaaaaaa");

        assertThrows(PasswordException.class, () -> adminService.save(admin));
    }

    @Test
    void test_save_with_not_min_length_pass() {
        Admin admin = new Admin();
        admin.setFirstName("First-4");
        admin.setLastName("Last-4");
        admin.setEmail("Email-4@mail.com");
        admin.setPassword("123ab");

        assertThrows(PasswordException.class, () -> adminService.save(admin));
    }

    @Test
    void test_load_by_email() {
        String email = "Email-1@mail.com";
        Admin result = adminService.loadByEmail(email);

        System.out.println(result);
        assertEquals("Email-1@mail.com", result.getEmail());
    }

    @Test
    void test_update_isOk() {
        long id = 5;
        Admin admin = new Admin();
        admin.setId(id);
        admin.setFirstName("First-3-updated");
        admin.setLastName("Last-3");
        admin.setEmail("Email-3@mail.com");
        admin.setPassword("123asd45");
        Admin result = adminService.update(admin);

        assertEquals("First-3-updated", result.getFirstName());
    }

    @Test
    void test_update_with_duplicate_email() {
        long id = 5;
        Admin admin = new Admin();
        admin.setId(id);
        admin.setFirstName("First-3-updated");
        admin.setLastName("Last-3");
        admin.setEmail("Email-1@mail.com");
        admin.setPassword("123asd45");

        assertThrows(EmailException.class, () -> adminService.update(admin));
    }

    @Test
    void test_load_by_id_exist() {
        long id = 1;
        Admin result = adminService.loadById(id);
        assertNotNull(result);
    }

    @Test
    void test_load_by_id_not_exist() {
        long id = 11;
        assertThrows(ResourceNotFoundException.class, () -> adminService.loadById(id));
    }

    @Test
    void test_load_all() {
        List<Admin> adminList = adminService.loadAll();
        assertEquals(3, adminList.size());
    }

    @Test
    void test_delete(){
        long id = 5;
        adminService.deleteById(id);

        List<Admin> adminList = adminService.loadAll();
        assertEquals(2, adminList.size());
    }

    @Test
    void test_delete_not_exist(){
        long id = 3;
        assertThrows(EmptyResultDataAccessException.class, () -> adminService.deleteById(id));
    }
}