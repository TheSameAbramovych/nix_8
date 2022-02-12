package ua.com.alevel.module_3.exception;

public class EmptyNameException extends RuntimeException {
    public EmptyNameException() {
        super("Field is empty!");
    }
}
