package ua.com.hw4;

import ua.com.hw4.view.GroupController;
import ua.com.hw4.view.StudentController;

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
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private static void runNavigation() {
        System.out.println("StudentCrud -> 1");
        System.out.println("GroupCrud -> 2");
        System.out.println("Выход -> 0");
        System.out.println();
    }

    private static void runEntityCrud(String position, BufferedReader reader) {
        switch (position) {
            case "1" -> new StudentController().run(reader);
            case "2" -> new GroupController().run(reader);
        }
    }

}
