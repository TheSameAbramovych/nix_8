package ua.com.alevel;

import java.util.Scanner;

public class School {
    public School() {
        System.out.print("Введите номер урока: ");
        Scanner h = new Scanner(System.in);
        int l = h.nextInt();
        if (l <= 10 && l > 0) {
            int time = 9 * 60;
            time += l * 45;
            int longBreak = 15 * ((l - 1) / 2);
            int shortBreak = 5 * (l - ((l + 1) / 2));
            time += longBreak + shortBreak;
            int hours = time / 60;
            int minutes = time - (hours * 60);
            System.out.println(hours + ":" + minutes);
        } else {
            System.out.println("Некорректное число уроков!");
        }
    }
}