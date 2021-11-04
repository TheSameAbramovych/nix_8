package ua.com.alevel;

import ua.com.alevel.level_1.HorseGame;
import ua.com.alevel.level_1.TriangleArea;
import ua.com.alevel.level_1.UniqueElement;
import ua.com.alevel.level_2.BinaryTree;
import ua.com.alevel.level_2.BracketsSequence;
import ua.com.alevel.level_3.LifeOfGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartModule1Main {
    public static void main(String[] args) {
        System.out.println("Run UniqueElement tap -> 1");
        System.out.println("Run HorseGame tap -> 2");
        System.out.println("Run TriangleArea tap -> 3");
        System.out.println("Run BracketsSequence tap -> 4");
        System.out.println("Run BinaryTree tap -> 5");
        System.out.println("Run LifeOfGame tap -> 6");
        System.out.println("Exit tap -> 0");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String event;
        try {
            while ((event = reader.readLine()) != null) {
                switch (event) {
                    case "1" -> new UniqueElement();
                    case "2" -> new HorseGame();
                    case "3" -> new TriangleArea();
                    case "4" -> new BracketsSequence();
                    case "5" -> new BinaryTree();
                    case "6" -> new LifeOfGame();
                    case "0" -> System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
