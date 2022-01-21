package ir.maktab.hsps.controller;

import ir.maktab.hsps.api.transaction.TransactionCreateParam;
import ir.maktab.hsps.api.transaction.TransactionCreateResult;
import ir.maktab.hsps.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/online")
    public ResponseEntity<TransactionCreateResult> payOnline(@RequestBody TransactionCreateParam createParam) {
        TransactionCreateResult transactionCreateResult = transactionService.payOnline(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionCreateResult);
    }

    @PostMapping("/online")
    public ResponseEntity<TransactionCreateResult> payWithCredit(@RequestBody TransactionCreateParam createParam) {
        TransactionCreateResult transactionCreateResult = transactionService.payWithCredit(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionCreateResult);
    }

}
