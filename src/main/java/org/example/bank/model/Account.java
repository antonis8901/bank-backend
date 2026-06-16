package org.example.bank.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq", sequenceName = "account_sequence", allocationSize = 1)
    private int id;

    @Column(nullable = false)
    private double balance;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
