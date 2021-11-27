package ua.com.alevel;

import ua.com.alevel.view.GroupController;
import ua.com.alevel.view.StudentController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogsAndTestMain {

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();
        System.out.println("Выбирай : ");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                if (position.equals("0")) {
                    System.exit(0);
                }
                runEntityCrud(position, reader);
                runNavigation();
            }
        } catch (IOException e) {
            System.out.println("Проблемка : = " + e.getMessage());
        }
    }

    private static void runNavigation() {
        System.out.println();
        System.out.println("""
                Меню управления студентами -> 1
                Меню управления групами -> 2
                Выход с программы -> 0
                """);
    }

    private static void runEntityCrud(String position, BufferedReader reader) {
        switch (position) {
            case "1" -> new StudentController().run(reader);
            case "2" -> new GroupController().run(reader);
        }
    }

}
