package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.Transaction;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private HomeServiceOfferService homeServiceOfferService;

    @Test
    void test_save() {
        HomeServiceOffer homeServiceOffer = homeServiceOfferService.loadById(2L);
        HomeServiceOrder homeServiceOrder = homeServiceOffer.getHomeServiceOrder();
        Proficient proficient = homeServiceOffer.getProficient();
        Customer customer = homeServiceOrder.getCustomer();
        Double amount = homeServiceOffer.getSuggestedPrice();

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setCustomer(customer);
        transaction.setProficient(proficient);
        transaction.setHomeServiceOffer(homeServiceOffer);
        transaction.setHomeServiceOrder(homeServiceOrder);

        Transaction result = transactionService.save(transaction);
        assertNotNull(result);
    }
}