package ir.maktab.hsps.service;

import ir.maktab.hsps.api.address.AddressCreateParam;
import ir.maktab.hsps.api.address.AddressUpdateParam;
import ir.maktab.hsps.api.home_service_order.HomeServiceOrderCreateParam;
import ir.maktab.hsps.api.home_service_order.HomeServiceOrderCreateResult;
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

    @Test
    void test_saveHomeServiceOrder() {
        AddressCreateParam  addressCreateParam = AddressCreateParam.builder()
                .province("Province-7")
                .city("City-7")
                .street("Street-7")
                .alley("Alley-7")
                .plaque("7")
                .build();
        HomeServiceOrderCreateParam createParam = HomeServiceOrderCreateParam.builder()
                .subCategoryId(6)
                .customerId(3)
                .address(addressCreateParam)
                .comment("Customer 3 order")
                .suggestedPrice(16500.0)
                .build();

        HomeServiceOrderCreateResult homeServiceOrderCreateResult = homeServiceOrderService.saveHomeServiceOrder(createParam);
        assertNotNull(homeServiceOrderCreateResult);
    }

    @Test
    void test_load_by_id() {
//        HomeServiceOrder homeServiceOrder = homeServiceOrderService.loadById(4L);
//        assertNotNull(homeServiceOrder);
    }

    @Test
    void test_accept_offer_isOk() {
        // TODO
//        HomeServiceOrder homeServiceOrder = homeServiceOrderService.loadById(4L);
//        HomeServiceOffer acceptedOffer = homeServiceOfferService.loadById(3L);
//        homeServiceOrder.setAcceptedOffer(acceptedOffer);
//        HomeServiceOrder result = homeServiceOrderService.acceptOffer(homeServiceOrder);
//        assertNotNull(result);
    }

    @Test
    void acceptOffer() {
    }
}