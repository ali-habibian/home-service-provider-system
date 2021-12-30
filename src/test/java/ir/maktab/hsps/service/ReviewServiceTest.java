package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.Review;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private HomeServiceOfferService homeServiceOfferService;

    @Test
    void test_save() {
        Review review = new Review();
        HomeServiceOffer homeServiceOffer = homeServiceOfferService.loadById(2L);
        Proficient proficient = homeServiceOffer.getProficient();
        Customer customer = customerService.loadById(1L);

        review.setComment("Comment-1");
        review.setRating(5);
        review.setCustomer(customer);
        review.setHomeServiceOffer(homeServiceOffer);
        review.setProficient(proficient);

        Review result = reviewService.save(review);
        assertNotNull(result);
    }
}