package ua.com.alevel;

import java.util.Arrays;
import java.util.Scanner;

public class CharCalculate {
    public CharCalculate() {

        System.out.print("Печатайте: ");
        Scanner g = new Scanner(System.in);
        String h = g.nextLine();

        char[] ch = h.toCharArray();
        Arrays.sort(ch);

        char symbol = ch[0];
        int len = ch.length;
        int index = 0;
        int count = 0;
        for (int i = 0; i < len; i++) {
            if (Character.isAlphabetic(ch[i])) {
                if (ch[i] == symbol) {
                    count++;
                } else {
                    System.out.println(++index + ". " + symbol + ": " + count);
                    count = 1;
                    symbol = ch[i];
                }
            }
        }
        System.out.println(++index + ". " + symbol + ": " + count);
    }
}
