package ua.com.alevel.module_3.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ua.com.alevel.module_3.dao.TransactionDao;
import ua.com.alevel.module_3.entity.Operation;
import ua.com.alevel.module_3.entity.Transaction;
import ua.com.alevel.module_3.entity.User;
import ua.com.alevel.module_3.entity.Wallet;
import ua.com.alevel.module_3.exception.UserNotFoundException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class TransactionReportCreatorImpl implements TransactionReportCreator {
    private static final List<String> CSV_HEADERS = List.of(
            "Processed date", "Wallet", "Target wallet", "Operation", "Amount", "Currency", "Transaction amount",
            "Transaction currency", "Transaction exchange rate");

    private final TransactionDao transactionDao;
    private final UserService userService;

    public TransactionReportCreatorImpl(TransactionDao transactionDao, UserService userService) {
        this.transactionDao = transactionDao;
        this.userService = userService;
    }

    @Transactional
    public byte[] createCsvReport(Long userId, String wallet, Date from, Date to) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        List<String> wallets = StringUtils.hasLength(wallet) ? List.of(wallet) : user.getWallets().stream().map(Wallet::getNumber).toList();
        List<Transaction> transactions = transactionDao.findByPeriod(wallets, from, to);

        Stream<List<String>> transactionStream = transactions.stream()
                .sorted(Comparator.comparing(Transaction::getProcessedDate).reversed())
                .map(this::convertToRow);
        return Stream.concat(Stream.of(CSV_HEADERS), transactionStream)
                .map(this::convertToCSV)
                .collect(Collectors.joining("\n"))
                .getBytes();
    }

    private List<String> convertToRow(Transaction transaction) {
        String primaryWallet = transaction.getWallet().getNumber();
        String targetWallet = transaction.getDependencyWallet();
        String processedDate = transaction.getProcessedDate().toString();
        String operation = transaction.getOperation().name();
        String walletCurrency = transaction.getWallet().getCurrency().name();
        String transactionCurrency = transaction.getCurrency().name();
        BigDecimal transactionExchangeRate = transaction.getExchangeRate();
        BigDecimal transactionAmount = Operation.CREDIT.equals(transaction.getOperation()) ?
                transaction.getAmount() : transaction.getAmount().negate();
        String walletAmount = transactionAmount.multiply(transactionExchangeRate).toString();

        return List.of(processedDate, primaryWallet, targetWallet,
                operation, walletAmount, walletCurrency,
                transactionAmount.toString(), transactionCurrency, transactionExchangeRate.toString());
    }

    public String convertToCSV(List<String> data) {
        return data.stream()
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(", "));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
