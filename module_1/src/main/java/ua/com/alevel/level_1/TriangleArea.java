package ua.com.alevel.level_1;

import java.util.Scanner;

public class TriangleArea {
    String str;
    int[] array;

    public TriangleArea() {
        System.out.println("Print coordinate dot A");
        ArraysInteger();
        int x1 = array[0];
        int y1 = array[1];

        System.out.println("Print coordinate dot B");
        ArraysInteger();
        int x2 = array[0];
        int y2 = array[1];

        System.out.println("Print coordinate dot C : ");
        ArraysInteger();
        int x3 = array[0];
        int y3 = array[1];

        double square = 0.5 * Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1));
        System.out.println("Square triangular = " + square);
    }

    public void ArraysInteger() {
        Scanner scan = new Scanner(System.in);
        str = scan.nextLine();

        String[] arrStr = str.split(",");
        array = new int[arrStr.length];

        for (int i = 0; i < arrStr.length; i++) {
            array[i] = Integer.parseInt(arrStr[i]);
        }
    }

}
