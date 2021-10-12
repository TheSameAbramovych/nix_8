package ua.com.alevel;

import java.util.Scanner;

public class NumberCalculate {
    public NumberCalculate() {
        System.out.println("Печатайте: ");
        Scanner h = new Scanner(System.in);
        String s1 = h.nextLine();
        char[] ch = s1.toCharArray();
        int len = ch.length;
        int cha;
        int sum1 = 0;
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(ch[i])) {
                cha = Integer.parseInt(String.valueOf(ch[i]));
                sum1 = sum1 + cha;
            }
        }
        System.out.println(sum1);
    }
}