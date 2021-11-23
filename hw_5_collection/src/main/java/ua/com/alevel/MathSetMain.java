package ua.com.alevel;

import ua.com.alevel.util.MathSet;

public class MathSetMain {
    public static void main(String[] args) {
        Integer[] ints = {1, 2, 3};
        Integer[] ints2 = {4, 5, 6};
        MathSet<Integer> mathSet = new MathSet<>(1, 2);

        ints[2] = 4;

        System.out.println(mathSet.get(2));
    }
}
