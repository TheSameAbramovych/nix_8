package ua.com.alevel.module_3.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.alevel.module_3.dao.TransactionDao;
import ua.com.alevel.module_3.entity.Currency;
import ua.com.alevel.module_3.entity.Operation;
import ua.com.alevel.module_3.entity.Transaction;
import ua.com.alevel.module_3.entity.Wallet;
import ua.com.alevel.module_3.exception.CloseWalletException;
import ua.com.alevel.module_3.exception.InsufficientAmountException;
import ua.com.alevel.module_3.exception.NotFoundWalletException;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDao transactionDao;
    private final WalletService walletService;
    private final CurrencyService currencyService;


    @Override
    @Transactional
    public void transfer(Wallet wallet, BigDecimal amount, String walletNumber) {
        checkIsClose(wallet);
        checkInsufficientAmount(wallet, amount);

        Wallet targetWallet = walletService.findByNumber(walletNumber);
        if (targetWallet == null) {
            throw new NotFoundWalletException(walletNumber);
        }
        checkIsClose(targetWallet);

        Currency currency = wallet.getCurrency();
        BigDecimal exchangeBuyRate = currencyService.getExchangeBuyRate(currency, targetWallet.getCurrency());
        BigDecimal targetAmount = amount.multiply(exchangeBuyRate);
        targetWallet.setAmount(targetWallet.getAmount().add(targetAmount));

        transactionDao.create(createTransaction(wallet, amount, targetWallet, BigDecimal.ONE, Operation.DEBIT));
        transactionDao.create(createTransaction(targetWallet, targetAmount, wallet, exchangeBuyRate, Operation.CREDIT));
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
