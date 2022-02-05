package ua.com.alevel.module_3.exception;

public class CloseWalletException extends RuntimeException {
    public CloseWalletException(String walletNumber) {
        super("Wallet " + walletNumber + " is close");
    }
}
