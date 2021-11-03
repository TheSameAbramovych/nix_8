package ua.com.alevel.level_1;

import java.util.Scanner;

public class TriangleArea {

    public TriangleArea() {
        System.out.println("Print coordinate dot A: ");
        int[] array = readDot();
        int x1 = array[0];
        int y1 = array[1];

        System.out.println("Print coordinate dot B: ");
        array = readDot();
        int x2 = array[0];
        int y2 = array[1];

        System.out.println("Print coordinate dot C: ");
        array = readDot();
        int x3 = array[0];
        int y3 = array[1];

        double square = 0.5 * Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1));
        System.out.println("Square triangular = " + square);
    }

    public int[] readDot() {
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
