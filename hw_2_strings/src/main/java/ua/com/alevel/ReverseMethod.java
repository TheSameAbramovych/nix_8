package ua.com.alevel;

import java.util.Scanner;

public class ReverseMethod {
    public static void main(String[] args) {
        System.out.println("Print : ");
        Scanner g = new Scanner(System.in);
        String str = g.nextLine();

        String[] arrStr = str.split(" ");
        for (String s : arrStr) {
            char[] ch = s.toCharArray();
            for (int j = ch.length - 1; j >= 0; j--) {
                System.out.print(ch[j]);
            }
            System.out.print(" ");
        }
    }
}



