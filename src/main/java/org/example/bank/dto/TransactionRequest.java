package org.example.bank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private int userId;
    private double amount;
}
