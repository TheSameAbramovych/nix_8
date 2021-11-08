package ua.com.alevel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static ua.com.alevel.EntityTestHelper.*;

public class EntityUtilTest {

    @Test
    public void shouldDoReturnNameArrayWhenFullNameIsNotEmpty() {
        String[] names = EntityUtil.generateFirstAndLastName(FULL_NAME);

        Assertions.assertEquals(2, names.length);
        Assertions.assertEquals(names[0], FIRST_NAME);
        Assertions.assertEquals(names[1], LAST_NAME);
    }

    @Test
    public void shouldDoReturnNameArrayWhenFullNameIsEmpty() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> EntityUtil.generateFirstAndLastName(null),
                "fullName is not valid");
    }

    @Test
    public void shouldDoReturnEncryptPasswordWhenUserPasswordIsNotEmpty() {
        String encryptPassword = EntityUtil.generateEncryptPassword(ORIGINAL_PASSWORD);

        Assertions.assertEquals(ENCRYPT_PASSWORD, encryptPassword);
    }

    @Test
    public void shouldDoReturnEncryptPasswordWhenUserPasswordIsEmpty() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> EntityUtil.generateEncryptPassword(null),
                "password can not be empty");
    }
}
