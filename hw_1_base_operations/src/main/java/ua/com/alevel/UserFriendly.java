package ua.com.alevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserFriendly {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String event;
        try {
            while ((event = reader.readLine()) != null) {
                switch (event) {
                    case "1": {
                        new School();
                    }
                    break;
                    case "2": {
                        new NumberCalculate();
                    }
                    break;
                    case "3": {
                        new CharCalculate();
                    }
                    break;
                    case "0": {
                        System.exit(0);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

