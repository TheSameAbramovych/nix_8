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
        int len = ch.length;
        if (len < 2) {
            System.out.println("Введите больше одного значения!");
        } else {
            int index = 0;
            int count = 0;
            char symbol = ch[len - 1];
            for (int i = len - 1; i >= 0; i--) {
                if (Character.isLetter(ch[i])) {
                    if (ch[i] == symbol) {
                        count++;
                    } else {
                        System.out.println(++index + ". " + symbol + ": " + count);
                        count = 1;
                        symbol = ch[i];
                    }
                }
            }
            if (Character.isLetter(symbol)) {
                System.out.println(++index + ". " + symbol + ": " + count);
            } else {
                System.out.println("Вы ввели только цифры!");
            }
        }
    }
}