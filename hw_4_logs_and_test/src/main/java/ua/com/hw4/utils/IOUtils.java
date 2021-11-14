package ua.com.hw4.utils;

import java.util.Scanner;

public final class IOUtils {

    private IOUtils() {
    }

    public static String[] readStringArray() {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        return str.split(",");
    }
}
