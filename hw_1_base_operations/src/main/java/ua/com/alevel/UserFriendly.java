package ua.com.alevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserFriendly {
    public static void main(String[] args) {
        System.out.println("Здравствуйте!");
        System.out.println("Для выбора задания следует нажать: ");
        System.out.println("1 - Подсчёт суммы цифр в строке");
        System.out.println("2 - Подсчёт суммы букв в строке");
        System.out.println("3 - Информация об окончании уроков");
        System.out.println("0 - выход с программы");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String event;
        try {
            while ((event = reader.readLine()) != null) {
                switch (event) {
                    case "1" -> new NumberCalculate();
                    case "2" -> new CharCalculate();
                    case "3" -> new School();
                    case "0" -> System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

