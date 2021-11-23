package ua.com.alevel.alevel.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public final class IOUtils {

    private IOUtils() {
    }

    public static String[] readStringArray() {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        return str.split(",");
    }

    public static String readOrDefault(String defaultStr, BufferedReader reader) throws IOException {
        String str = reader.readLine();
        return "".equals(str) ? defaultStr : str;
    }
}
