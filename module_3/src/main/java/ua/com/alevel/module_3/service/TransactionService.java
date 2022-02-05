package ua.com.alevel.module_3.service;

import ua.com.alevel.module_3.entity.Wallet;

import java.math.BigDecimal;

public interface TransactionService {
    void transfer(Wallet wallet, BigDecimal amount, String walletNumber);
}
