package ua.com.alevel.level_1;

import ua.com.alevel.helper.IOHelper;

public class HorseGame {
    public HorseGame() {
        try {
            System.out.println("Enter start coordinate x,y");
            int[] array = IOHelper.readIntArray();
            int x = array[0];
            int y = array[1];
            ;

            System.out.println("Enter next Step coordinate x,y");
            array = IOHelper.readIntArray();
            int x1 = array[0];
            int y1 = array[1];

            if ((x1 == x + 1 && y1 == y + 2)
                    || (x1 == x + 2 && y1 == y + 1)
                    || (x1 == x - 1 && y1 == y - 2)
                    || (x1 == x - 2 && y1 == y - 1)) {
                System.out.println("True");
            } else {
                System.out.println("False");
            }
        } catch (Exception e) {
            System.out.println("Incorrect input!");
        }
    }
}


