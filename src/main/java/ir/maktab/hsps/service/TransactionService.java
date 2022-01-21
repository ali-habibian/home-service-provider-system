package ir.maktab.hsps.service;

import ir.maktab.hsps.api.transaction.TransactionCreateParam;
import ir.maktab.hsps.api.transaction.TransactionCreateResult;
import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.Transaction;
import ir.maktab.hsps.entity.order.HomeServiceOrder;
import ir.maktab.hsps.entity.order.OrderStatus;
import ir.maktab.hsps.entity.user.Customer;
import ir.maktab.hsps.entity.user.Proficient;
import ir.maktab.hsps.exception.CreditException;
import ir.maktab.hsps.exception.ResourceNotFoundException;
import ir.maktab.hsps.exception.TransactionException;
import ir.maktab.hsps.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;

    @Transactional
    public TransactionCreateResult payOnline(TransactionCreateParam createParam) {
        Customer customer = customerService.loadById(createParam.getCustomerId());

        HomeServiceOrder homeServiceOrder = customer.getHomeServiceOrders().stream()
                .filter(o -> o.getId() == createParam.getOrderId())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("HomeServiceOrder", "id", createParam.getOrderId()));

        if (homeServiceOrder.getOrderStatus() != OrderStatus.FINISHED) {
            throw new TransactionException("Order not finished");
        }

        HomeServiceOffer homeServiceOffer = homeServiceOrder.getAcceptedOffer();

        return getTransactionCreateResult(createParam, customer, homeServiceOrder, homeServiceOffer);
    }

    @Transactional
    public TransactionCreateResult payWithCredit(TransactionCreateParam createParam) {
        createParam.setCustomerCreditCardNumber("Credit");

        Customer customer = customerService.loadById(createParam.getCustomerId());

        HomeServiceOrder homeServiceOrder = customer.getHomeServiceOrders().stream()
                .filter(o -> o.getId() == createParam.getOrderId())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("HomeServiceOrder", "id", createParam.getOrderId()));

        HomeServiceOffer homeServiceOffer = homeServiceOrder.getAcceptedOffer();

        if (homeServiceOrder.getOrderStatus() != OrderStatus.FINISHED) {
            throw new TransactionException("Order not finished");
        }

        if (customer.getCredit() < homeServiceOffer.getSuggestedPrice()) {
            throw new CreditException("Credit is not enough");
        }

        customer.setCredit(customer.getCredit() - homeServiceOffer.getSuggestedPrice());

        return getTransactionCreateResult(createParam, customer, homeServiceOrder, homeServiceOffer);
    }

    private TransactionCreateResult getTransactionCreateResult(TransactionCreateParam createParam, Customer customer, HomeServiceOrder homeServiceOrder, HomeServiceOffer homeServiceOffer) {
        Proficient proficient = homeServiceOffer.getProficient();

        DecimalFormat df = new DecimalFormat("0.00");
        proficient.setCredit(Double.parseDouble(df.format((proficient.getCredit() + (homeServiceOffer.getSuggestedPrice() * 0.7)))));

        homeServiceOrder.setOrderStatus(OrderStatus.PAID);

        Transaction transaction = createParam.convert2Transaction(customer, proficient, homeServiceOrder, homeServiceOffer);

        Transaction result = transactionRepository.save(transaction);

        return new TransactionCreateResult(result.getId());
    }
}
