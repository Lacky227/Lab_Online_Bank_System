package org.veedev.transactionservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.veedev.transactionservice.dto.TransactionRequest;
import org.veedev.transactionservice.dto.TransferRequest;
import org.veedev.transactionservice.service.TransactionService;

@RestController
@RequestMapping("/trans")
@AllArgsConstructor
@CrossOrigin
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody TransactionRequest transactionRequest) {
       return transactionService.deposit(transactionRequest);
    }
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody TransactionRequest transactionRequest) {
        return transactionService.withdraw(transactionRequest);
    }
}
