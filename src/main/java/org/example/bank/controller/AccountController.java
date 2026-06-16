package org.example.bank.controller;

import org.example.bank.dto.BalanceResponse;
import org.example.bank.dto.TransactionRequest;
import org.example.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/balance/{userId}")
    public ResponseEntity<?> checkBalance(@PathVariable int userId) {
        try {
            BalanceResponse response = accountService.getBalance(userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody TransactionRequest request) {
        try {
            BalanceResponse response = accountService.deposit(request.getUserId(), request.getAmount());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody TransactionRequest request) {
        try {
            BalanceResponse response = accountService.withdraw(request.getUserId(), request.getAmount());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
