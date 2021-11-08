package ua.com.alevel.level_1;

import ua.com.alevel.helper.IOHelper;

public class TriangleArea {

    public TriangleArea() {
        try {
            System.out.print("Enter coordinate dot A x,y : ");
            int[] array = IOHelper.readIntArray();
            int x1 = array[0];
            int y1 = array[1];

            System.out.print("Enter coordinate dot B x,y : ");
            array = IOHelper.readIntArray();
            int x2 = array[0];
            int y2 = array[1];

            System.out.print("Enter coordinate dot C x,y : ");
            array = IOHelper.readIntArray();
            int x3 = array[0];
            int y3 = array[1];

            double square = 0.5 * Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1));
            System.out.println("Square triangular = " + square);
        } catch (Exception e) {
            System.out.println("Incorrect input!");
        }
    }
}
