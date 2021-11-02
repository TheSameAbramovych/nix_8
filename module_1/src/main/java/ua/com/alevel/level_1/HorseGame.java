package ua.com.alevel.level_1;

import java.util.Scanner;

public class HorseGame {
    public HorseGame() {
        System.out.println("Start coordinate");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] arrStr = str.split(",");
        int x = Integer.parseInt(arrStr[0]);
        int y = Integer.parseInt(arrStr[1]);

        System.out.println("Next Step");
        Scanner scan = new Scanner(System.in);
        String str1 = scan.nextLine();
        String[] arrayStr = str1.split(",");
        int x1 = Integer.parseInt(arrayStr[0]);
        int y1 = Integer.parseInt(arrayStr[1]);

        if ((x1 == x + 1 && y1 == y + 2)
                || (x1 == x + 2 && y1 == y + 1)
                || (x1 == x - 1 && y1 == y - 2)
                || (x1 == x - 2 && y1 == y - 1)) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
    }
}


