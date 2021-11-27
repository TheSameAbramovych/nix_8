package ua.com.alevel;

import ua.com.alevel.util.MathSet;

public class MathSetMain {
    public static void main(String[] args) {
        MathSet<Integer> mathSet = new MathSet<>();
        System.out.print("ADD(122, 12, 32, 11, 27, 21, 2, 3, 7, 9, 5, 12, 8, 9, 9, 12, 12, 23, 45): ");
        mathSet.add(122, 12, 32, 11, 27, 21, 2, 3, 7, 9, 5, 12, 8, 9, 9, 12, 12, 23, 45);
        System.out.println(mathSet);

        System.out.print("MIN: ");
        System.out.println(mathSet.getMin());
        System.out.print("MAX: ");
        System.out.println(mathSet.getMax());

        System.out.print("MEDIAN: ");
        System.out.println(mathSet.getMedian());

        mathSet.sortAsc(3);
        System.out.println("SORT_ASK(3): " + mathSet);

        mathSet.sortAsc();
        System.out.println("SORT_ASK: " + mathSet);

        mathSet.sortDesc(7);
        System.out.print("SORT_DESC(7): ");
        System.out.println(mathSet);

        mathSet.sortDesc();
        System.out.print("SORT_DESC: ");
        System.out.println(mathSet);

        System.out.println("CUT_ARRAY: " + mathSet.cut(0, 6));
        System.out.println("CUT(0,6): " + mathSet);

        System.out.print("AVERAGE: ");
        System.out.println(mathSet.getAverage());

        System.out.print("CLEAR(12): ");
        mathSet.clear(12);
        System.out.println(mathSet);

        System.out.print("JOIN(35, 36; 999, 666): ");
        mathSet.join(new MathSet<>(35, 36), new MathSet<>(999, 666));
        System.out.println(mathSet);

        System.out.print("INTERSECTION(9, 8): ");
        mathSet.intersection(new MathSet<>(9, 8));
        System.out.println(mathSet);

        mathSet.clear(999, 666);
        System.out.print("CLEAR(999,666): " + mathSet);

    }
}
