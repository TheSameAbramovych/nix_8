package ua.com.alevel.helper;

import java.util.Scanner;

public final class IOHelper {
    private IOHelper() {
    }

    public static int[] readIntArray() {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();

        String[] arrStr = str.split(",");
        int[] array = new int[arrStr.length];

        for (int i = 0; i < arrStr.length; i++) {
            array[i] = Integer.parseInt(arrStr[i]);
        }
        return array;
    }

}
