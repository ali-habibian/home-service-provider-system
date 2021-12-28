package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.Address;
import ir.maktab.hsps.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressServiceTest {
    @Autowired
    private AddressService addressService;

    @Test
    void test_save() {
        Address address = Address.builder()
                .province("Province-4")
                .city("City-4")
                .street("Street-4")
                .alley("Alley-4")
                .plaque("4")
                .build();

        Address result = addressService.save(address);
        assertNotNull(result);
    }

    @Test
    void test_update() {
        Address address = Address.builder()
                .id(2L)
                .province("Province-2-update")
                .city("City-2-update")
                .street("Street-2")
                .alley("Alley-2")
                .plaque("2")
                .build();

        Address result = addressService.save(address);
        assertEquals("Province-2-update", result.getProvince());
    }

    @Test
    void test_load_by_id_exist() {
        long id = 1;
        Address result = addressService.loadById(id);
        assertNotNull(result);
    }

    @Test
    void test_load_by_id_not_exist() {
        long id = 11;
        assertThrows(ResourceNotFoundException.class, () -> addressService.loadById(id));
    }

    @Test
    void test_load_all() {
        List<Address> addressList = addressService.loadAll();
        assertEquals(2, addressList.size());
    }

    @Test
    void test_delete(){
        long id = 4;
        addressService.deleteById(id);

        List<Address> addressList = addressService.loadAll();
        assertEquals(2, addressList.size());
    }

    @Test
    void test_delete_not_exist(){
        long id = 3;
        assertThrows(EmptyResultDataAccessException.class, () -> addressService.deleteById(id));
    }
}