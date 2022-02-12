package ua.com.alevel.module_3.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("User '" + userId + "' not exist");
    }
}
