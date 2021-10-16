package ua.com.alevel;

import java.util.Scanner;

public class ReverseMethod {
    public static void main(String[] args) {
        String s;
        StringBuilder rev = new StringBuilder();
        Scanner in = new Scanner(System.in);

        System.out.println("Печатайте: ");
        s = in.nextLine();

        int len = s.length();
        int l, r;
        int i = 0;
        while (i < len) {
            l = i;
            while (i < len && s.charAt(i) != ' ') {
                i++;
            }
            r = i - 1;
            for (int j = r; j >= l; j--) {
                rev.append(s.charAt(j));
            }
            rev.append(" ");
            i++;
        }

        System.out.println("Результат:  " + rev);
    }

}



