package ua.com.alevel.module_3.exception;

public class NotFoundWalletException extends RuntimeException {
    public NotFoundWalletException(String walletNumber) {
        super("Wallet " + walletNumber + " not exist");
    }
}
