package ua.com.alevel;

import ua.com.alevel.level_1.HorseGame;
import ua.com.alevel.level_1.TriangleArea;
import ua.com.alevel.level_1.UniqueElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartLevel_1 {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String event;
        try {
            while ((event = reader.readLine()) != null) {
                switch (event) {
                    case "1" -> new UniqueElement();
                    case "2" -> new HorseGame();
                    case "3" -> new TriangleArea();
                    case "0" -> System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
