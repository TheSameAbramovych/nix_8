package ua.com.alevel.module_3.exception;

public class CloseUserException extends RuntimeException {
    public CloseUserException(String user) {
        super("User '" + user + "' is close");
    }
}
