package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeServiceOfferServiceTest {

    @Autowired
    private HomeServiceOfferService homeServiceOfferService;

    @Autowired
    private HomeServiceOrderService homeServiceOrderService;

    @Autowired
    private ProficientService proficientService;

    @Test
    void test_save() {
        HomeServiceOffer homeServiceOffer = new HomeServiceOffer();

        Proficient proficient = proficientService.loadById(2L);
        HomeServiceOrder homeServiceOrder = homeServiceOrderService.loadById(4L);

        homeServiceOffer.setProficient(proficient);
        homeServiceOffer.setSuggestedPrice(18000.0);
        homeServiceOffer.setHomeServiceOrder(homeServiceOrder);

        HomeServiceOffer result = homeServiceOfferService.save(homeServiceOffer);
        assertNotNull(result);
    }

    @Test
    void test_update() {
        HomeServiceOffer homeServiceOffer = homeServiceOfferService.loadById(1L);
        homeServiceOffer.setSuggestedPrice(21000.0);
        HomeServiceOffer result = homeServiceOfferService.update(homeServiceOffer);
        assertEquals(21000.0, result.getSuggestedPrice());
    }

    @Test
    void test_delete() {
        homeServiceOfferService.deleteById(1L);
        assertThrows(ResourceNotFoundException.class, () -> homeServiceOfferService.loadById(1L));
    }

    @Test
    void test_send_offer_isOk() {
        HomeServiceOffer homeServiceOffer = new HomeServiceOffer();
        homeServiceOffer.setProficient(proficientService.loadById(7L));
        homeServiceOffer.setSuggestedPrice(6000.0);
        homeServiceOffer.setWorkDuration("2 Days");
        homeServiceOffer.setHomeServiceOrder(homeServiceOrderService.loadById(4L));

        HomeServiceOffer result = homeServiceOfferService.sendOffer(homeServiceOffer);
        assertNotNull(result);
    }

    @Test
    void test_load_by_order_Id_isOk() {
        List<HomeServiceOffer> homeServiceOffers = homeServiceOfferService.loadByOrderIdSortAsc(4);
        homeServiceOffers.forEach(ho -> {
            System.out.println(ho.getId() + ": " + ho.getSuggestedPrice());
        });
    }
}