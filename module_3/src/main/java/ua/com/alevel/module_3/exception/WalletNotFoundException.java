package ua.com.alevel.module_3.exception;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(String walletNumber) {
        super("Wallet '" + walletNumber + "' not exist");
    }
}
