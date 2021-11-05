package ua.com.alevel.level_1;

import ua.com.alevel.helper.IOHelper;

import java.util.Arrays;

public class UniqueElement {

    public UniqueElement() {
        try {
            System.out.println("Enter elements separating with a comma, without spaces:");
            int[] array = IOHelper.readIntArray();
            int numberOfElements = 0;
            Arrays.sort(array);

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
        } catch (Exception e) {
            System.out.println("Incorrect input!");
        }
    }
}

