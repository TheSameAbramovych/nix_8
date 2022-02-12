package ua.com.alevel.module_3.exception;

public class IncorrectNameException extends RuntimeException {
    public IncorrectNameException() {
        super("Must have only letters!");
    }
}
