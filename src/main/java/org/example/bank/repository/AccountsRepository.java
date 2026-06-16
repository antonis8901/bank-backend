package org.example.bank.repository;

import org.example.bank.model.Account;
import org.example.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUser(User user);
}
