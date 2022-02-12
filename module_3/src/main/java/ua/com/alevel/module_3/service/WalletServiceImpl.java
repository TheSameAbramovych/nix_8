package ua.com.alevel.module_3.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.alevel.module_3.dao.WalletDao;
import ua.com.alevel.module_3.entity.Currency;
import ua.com.alevel.module_3.entity.User;
import ua.com.alevel.module_3.entity.Wallet;
import ua.com.alevel.module_3.exception.CloseUserException;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletDao walletDao;

    @Override
    public Wallet findByNumber(String number) {
        return walletDao.findByNumber(number);
    }

    @Override
    public Wallet create(User user, Currency currency) {
        Wallet wallet = new Wallet();
        wallet.setAmount(BigDecimal.ZERO);
        wallet.setCurrency(currency);
        wallet.setNumber(UUID.randomUUID().toString());
        wallet.setUser(user);

        walletDao.create(wallet);
        return wallet;
    }

    @Override
    public void update(Wallet wallet) {
        walletDao.update(wallet);
    }

    public void changeStatusWallet(String numberWallet) {
        Wallet wallet = walletDao.findByNumber(numberWallet);
        if (!wallet.getUser().isActive()) {
            throw new CloseUserException(wallet.getUser().getId().toString());
        }
        wallet.setClose(!wallet.isClose());
        walletDao.update(wallet);
    }
}