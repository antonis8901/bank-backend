package org.example.bank.service;

import org.example.bank.dto.AuthRequest;
import org.example.bank.dto.AuthResponse;
import org.example.bank.model.Account;
import org.example.bank.model.User;
import org.example.bank.repository.AccountsRepository;
import org.example.bank.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public AuthResponse register(AuthRequest request) {
        if (usersRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user = usersRepository.save(user);

        Account account = new Account();
        account.setBalance(0.0);
        account.setUser(user);
        accountsRepository.save(account);

        return new AuthResponse("Account created successfully", user.getId(), user.getUsername(), 0.0);
    }

    public AuthResponse login(AuthRequest request) {
        User user = usersRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        Account account = accountsRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return new AuthResponse("Login successful", user.getId(), user.getUsername(), account.getBalance());
    }
}
