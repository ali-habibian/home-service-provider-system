package ir.maktab.hsps.service;

import ir.maktab.hsps.entity.Transaction;
import ir.maktab.hsps.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TransactionService extends BaseService<Transaction, Long> {
    private final TransactionRepository transactionRepository;

    @PostConstruct
    public void init() {
        setJpaRepository(transactionRepository);
    }
}
