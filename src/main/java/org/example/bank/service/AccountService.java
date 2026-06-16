package org.example.bank.service;

import org.example.bank.dto.BalanceResponse;
import org.example.bank.model.Account;
import org.example.bank.model.User;
import org.example.bank.repository.AccountsRepository;
import org.example.bank.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private UsersRepository usersRepository;

    public List<Account> getAllAccounts() {
        return accountsRepository.findAll();
    }

    private Account getAccountByUserId(int userId) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return accountsRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public BalanceResponse getBalance(int userId) {
        Account account = getAccountByUserId(userId);
        return new BalanceResponse(account.getBalance(), "Current balance retrieved");
    }

    public BalanceResponse deposit(int userId, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Deposit amount must be greater than zero");
        }
        Account account = getAccountByUserId(userId);
        account.setBalance(account.getBalance() + amount);
        accountsRepository.save(account);
        return new BalanceResponse(account.getBalance(), "Deposit successful");
    }

    public BalanceResponse withdraw(int userId, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Withdrawal amount must be greater than zero");
        }
        Account account = getAccountByUserId(userId);
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds. Current balance: " + account.getBalance());
        }
        account.setBalance(account.getBalance() - amount);
        accountsRepository.save(account);
        return new BalanceResponse(account.getBalance(), "Withdrawal successful");
    }
}
