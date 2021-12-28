package ir.maktab.hsps.repository;

import ir.maktab.hsps.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
