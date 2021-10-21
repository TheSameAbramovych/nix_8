package ua.com.alevel;

import java.util.Objects;
import java.util.Scanner;

public class ReverseMethod {
    public static void main(String[] args) {
        System.out.println("Вас приветствует метод Reverse");
        System.out.println("Простой реверс -> текст");
        System.out.println("Частичный реверс-> текст,та часть которую нужно развернуть");
        System.out.println("Реверс по диапазону -> текст,индекс начала реверса,индекс окончания реверса");
        System.out.println("0 - выход");
        while (true) {
            Scanner g = new Scanner(System.in);
            String str = g.nextLine();
            String out = "Некорректный ввод";

            String[] arrStr = str.split(",");
            if (arrStr.length == 1) {
                out = reverse(arrStr[0]);
            }

            if (arrStr.length == 2) {
                out = reverse(arrStr[0], arrStr[1]);
            }

            if (arrStr.length == 3) {
                String firstS = arrStr[1].strip();
                String secondS = arrStr[2].strip();
                if (firstS.chars().allMatch(Character::isDigit)
                        && secondS.chars().allMatch(Character::isDigit)
                        && Integer.parseInt(firstS) < Integer.parseInt(secondS)
                        && Integer.parseInt(firstS) < str.length()
                        && Integer.parseInt(secondS) < arrStr.length) {
                    out = reverse(arrStr[0], Integer.parseInt(firstS), Integer.parseInt(secondS));
                }
            }

            if (!Objects.equals(out, "0")) {
                System.out.println(out);
            } else {
                System.exit(0);
            }
        }

    }

    public static String reverse(String text) {
        String[] arrStr = text.split(" ");
        StringBuilder reversedText = new StringBuilder();
        for (String s : arrStr) {
            char[] ch = s.toCharArray();
            for (int j = ch.length - 1; j >= 0; j--) {
                reversedText.append(ch[j]);
            }
            reversedText.append(' ');
        }
        return reversedText.toString().strip();
    }

    public static String reverse(String text, String dest) {
        dest = dest.strip();
        if (text.contains(dest)) {
            return text.replace(dest, reverse(dest));
        }
        return text;
    }

    public static String reverse(String text, int indexStart, int indexEnd) {
        String dest = text.substring(indexStart, indexEnd);
        return reverse(text, dest);
    }
}


