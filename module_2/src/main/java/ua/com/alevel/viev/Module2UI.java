package ua.com.alevel.viev;

import ua.com.alevel.task1.Task1;
import ua.com.alevel.task2.Task2;
import ua.com.alevel.task3.Task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Module2UI {
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                if (position.equals("0")) {
                    System.exit(0);
                }
                crud(position);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("""
                Task 1, format date  -> 1
                Task 2, first unique element -> 2
                Task 3, short way to city-> 3
                Exit -> 0
                """);
    }

    private void crud(String position) {
        try {
            switch (position) {
                case "1" -> new Task1().calendar();
                case "2" -> new Task2().uniqueElem();
                case "3" -> Task3.run();
            }
            runNavigation();
        } catch (Exception e) {
            System.out.println("Problem: " + e.getMessage());
        }
    }
}
