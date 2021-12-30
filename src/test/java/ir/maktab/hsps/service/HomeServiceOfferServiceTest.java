package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.user.Proficient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}