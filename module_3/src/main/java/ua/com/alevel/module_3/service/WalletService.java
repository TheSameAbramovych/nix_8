package ua.com.alevel.module_3.service;

import ua.com.alevel.module_3.entity.Currency;
import ua.com.alevel.module_3.entity.User;
import ua.com.alevel.module_3.entity.Wallet;

public interface WalletService {
    Wallet findByNumber(String id);

    Wallet create(User user, Currency currency);

    void update(Wallet wallet);
}
