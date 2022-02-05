package ua.com.alevel.module_3.exception;

public class InsufficientAmountException extends RuntimeException {
    public InsufficientAmountException(String walletNumber) {
        super("Not enough money in the wallet " + walletNumber);
    }
}
