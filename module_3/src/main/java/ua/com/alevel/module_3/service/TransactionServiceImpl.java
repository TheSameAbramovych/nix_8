package ua.com.alevel.module_3.service;

import org.springframework.stereotype.Service;
import ua.com.alevel.module_3.dao.TransactionDao;
import ua.com.alevel.module_3.entity.Currency;
import ua.com.alevel.module_3.entity.Operation;
import ua.com.alevel.module_3.entity.Transaction;
import ua.com.alevel.module_3.entity.Wallet;
import ua.com.alevel.module_3.exception.CloseWalletException;
import ua.com.alevel.module_3.exception.IncorrectWalletNumber;
import ua.com.alevel.module_3.exception.InsufficientAmountException;
import ua.com.alevel.module_3.exception.WalletNotFoundException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDao transactionDao;
    private final WalletService walletService;
    private final CurrencyService currencyService;

    public TransactionServiceImpl(TransactionDao transactionDao, WalletService walletService, CurrencyService currencyService) {
        this.transactionDao = transactionDao;
        this.walletService = walletService;
        this.currencyService = currencyService;
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    @Transactional
    public void transfer(Wallet wallet, BigDecimal amount, String walletNumber) {
        checkInsufficientAmount(wallet, amount);

        if (Objects.equals(wallet.getNumber(), walletNumber)) {
            throw new IncorrectWalletNumber(walletNumber);
        }

        Wallet targetWallet = walletService.findByNumber(walletNumber);
        if (targetWallet == null) {
            throw new WalletNotFoundException(walletNumber);
        }
        checkIsClose(targetWallet);

        Currency currency = wallet.getCurrency();
        BigDecimal exchangeBuyRate = currencyService.getExchangeRate(currency, targetWallet.getCurrency());
        BigDecimal targetAmount = amount.multiply(exchangeBuyRate);

        wallet.setAmount(wallet.getAmount().subtract(amount));
        targetWallet.setAmount(targetWallet.getAmount().add(targetAmount));

        transactionDao.create(createTransaction(wallet, amount, targetWallet, BigDecimal.ONE, Operation.DEBIT));
        transactionDao.create(createTransaction(targetWallet, amount, wallet, exchangeBuyRate, Operation.CREDIT));
        walletService.update(wallet);
        walletService.update(targetWallet);
    }

    private Transaction createTransaction(Wallet wallet, BigDecimal amount, Wallet targetWallet,
                                          BigDecimal exchangeBuyRate, Operation operation) {
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setCurrency(Operation.DEBIT.equals(operation) ? wallet.getCurrency() : targetWallet.getCurrency());
        transaction.setAmount(amount);
        transaction.setExchangeRate(exchangeBuyRate);
        transaction.setOperation(operation);
        transaction.setDependencyWallet(targetWallet.getNumber());
        transaction.setProcessedDate(new Date());
        return transaction;
    }

    private void checkIsClose(Wallet wallet) {
        if (wallet.isClose()) {
            throw new CloseWalletException(wallet.getNumber());
        }
    }

    private void checkInsufficientAmount(Wallet wallet, BigDecimal amount) {
        if (BigDecimal.ZERO.compareTo(wallet.getAmount().subtract(amount)) > 0) {
            throw new InsufficientAmountException(wallet.getNumber());
        }
    }
}
