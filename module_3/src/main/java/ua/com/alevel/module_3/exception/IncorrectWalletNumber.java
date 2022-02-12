package ua.com.alevel.module_3.exception;

public class IncorrectWalletNumber extends RuntimeException {
    public IncorrectWalletNumber(String walletNumber) {
        super("Unable to transfer to the wallet you are transferring from : " + walletNumber);
    }
}
