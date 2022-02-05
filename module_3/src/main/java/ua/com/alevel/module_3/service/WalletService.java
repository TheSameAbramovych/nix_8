package ua.com.alevel.module_3.service;

import ua.com.alevel.module_3.entity.Wallet;

public interface WalletService {
    Wallet findByNumber(String id);
}
