package ir.maktab.hsps.api.transaction;

import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.Transaction;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionCreateParam {
    private long customerId;
    private long orderId;
    private String customerCreditCardNumber;
    private String cvv2;
    private String expressionDate;
    private Double amount;
    private String captchaResponse;

    public Transaction convert2Transaction(Customer customer, Proficient proficient, HomeServiceOrder homeServiceOrder, HomeServiceOffer homeServiceOffer) {
        return Transaction.builder()
                .customer(customer)
                .proficient(proficient)
                .homeServiceOrder(homeServiceOrder)
                .homeServiceOffer(homeServiceOffer)
                .customerCreditCardNumber(this.customerCreditCardNumber)
                .amount(homeServiceOffer.getSuggestedPrice())
                .build();
    }
}
