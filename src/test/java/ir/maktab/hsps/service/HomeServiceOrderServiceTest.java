package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.Address;
import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.category.SubCategory;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.user.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class HomeServiceOrderServiceTest {

    @Autowired
    private HomeServiceOrderService homeServiceOrderService;

    @Autowired
    private HomeServiceOfferService homeServiceOfferService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Test
    void test_save() {
        Customer customer = customerService.loadById(2L);
        SubCategory subCategory = subCategoryService.loadById(1L);
        Address address = customer.getAddress();
        HomeServiceOrder homeServiceOrder = new HomeServiceOrder();
        homeServiceOrder.setCustomer(customer);
        homeServiceOrder.setSubCategory(subCategory);
        homeServiceOrder.setSuggestedPrice(10000.0);
        homeServiceOrder.setComment("Home service order 3");
        homeServiceOrder.setAddress(address);

        HomeServiceOrder result = homeServiceOrderService.save(homeServiceOrder);
        System.out.println("result.getId() = " + result.getId());
        assertNotNull(result);
    }

    @Test
    void test_load_by_id() {
        HomeServiceOrder homeServiceOrder = homeServiceOrderService.loadById(4L);
        assertNotNull(homeServiceOrder);
    }

    @Test
    void test_accept_offer_isOk() {
        HomeServiceOrder homeServiceOrder = homeServiceOrderService.loadById(4L);
        HomeServiceOffer acceptedOffer = homeServiceOfferService.loadById(3L);
        homeServiceOrder.setAcceptedOffer(acceptedOffer);
        HomeServiceOrder result = homeServiceOrderService.acceptOffer(homeServiceOrder);
        assertNotNull(result);
    }

}