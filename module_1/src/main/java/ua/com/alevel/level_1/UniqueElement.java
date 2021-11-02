package ua.com.alevel.level_1;

import java.util.Arrays;
import java.util.Scanner;

public class UniqueElement {

    public UniqueElement() {
        System.out.println("Print:");
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        String[] arrStr = str.split(",");
        int[] array = new int[arrStr.length];
        int numberOfElements = 0;
        Arrays.sort(array);

        for (int i = 0; i < arrStr.length; i++) {
            array[i] = Integer.parseInt(arrStr[i]);
        }

        for (int i = 0; i < array.length; i++) {
            if (i + 1 != array.length) {
                if (array[i] != array[i + 1]) {
                    numberOfElements++;
                }
            } else {
                numberOfElements++;
            }
        }
        System.out.println(numberOfElements);
    }
}

