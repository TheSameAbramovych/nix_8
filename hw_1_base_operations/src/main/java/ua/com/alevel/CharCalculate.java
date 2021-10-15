package ua.com.alevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CharCalculate {
    public CharCalculate() {
        System.out.print("Печатайте: ");
        Scanner g = new Scanner(System.in);
        String h = g.nextLine();
        boolean isNumeric = h.chars().allMatch(Character::isDigit);

        if (isNumeric) {
            System.out.println("Вы ввели только цыфры!");
        } else {
            char[] ch = h.toCharArray();
            Character[] newArray = IntStream.range(0, ch.length)
                    .mapToObj(i -> ch[i])
                    .toArray(Character[]::new);
            Arrays.sort(newArray);
            ArrayList<Character> newCh = new ArrayList<>();
            int len = newArray.length;
            int index = 0;
            int count = 0;

            IntStream.range(0, len).filter(i -> Character.isLetter(newArray[i])).forEach(i -> {
                newCh.add(newArray[i]);
            });
            int len1 = newCh.size();
            int j = 0;
            char symbol = newCh.get(0);

            while (j < len1) {
                if (newCh.get(j) == symbol) {
                    count++;
                } else {
                    System.out.println(++index + ". " + symbol + ": " + count);
                    count = 1;
                    symbol = newCh.get(j);
                }
                j++;
            }
            System.out.println(++index + ". " + symbol + ": " + count);
        }
    }
}
