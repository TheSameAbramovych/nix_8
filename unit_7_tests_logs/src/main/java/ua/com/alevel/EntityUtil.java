package ua.com.alevel;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public final class EntityUtil {

    private EntityUtil() {
    }

    public static String[] generateFirstAndLastName(String fullName) {
        if (StringUtils.isNotBlank(fullName)) {
            String[] names = fullName.split(" ");
            if (names.length == 2) {
                return names;
            }
        }
        throw new RuntimeException("fullName is not valid");
    }

    public static String generateEncryptPassword(String originalPassword) {
        if (StringUtils.isNotBlank(originalPassword)) {
            return DigestUtils.sha256Hex(originalPassword);
        }
        throw new RuntimeException("password can not be empty");
    }
}
