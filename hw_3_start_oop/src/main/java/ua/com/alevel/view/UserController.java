package ua.com.alevel.view;

import ua.com.alevel.entity.User;
import ua.com.alevel.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserController {

    private final UserService userService = new UserService();

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();
        System.out.println("Выбирай : ");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                crud(position, reader);
                position = reader.readLine();
                if (position.equals("0")) {
                    System.exit(0);
                }
                crud(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println("Создать юзер -> 1");
        System.out.println("Изменить данные юзера -> 2");
        System.out.println("Удалить юзер -> 3");
        System.out.println("Найти юзер по Id -> 4");
        System.out.println("Список всех юзеров -> 5");
        System.out.println("Выход -> 0");
        System.out.println();
    }

    private void crud(String position, BufferedReader reader) {
        switch (position) {
            case "1" -> create(reader);
            case "2" -> update(reader);
            case "3" -> delete(reader);
            case "4" -> findById(reader);
            case "5" -> findAll(reader);
        }
        runNavigation();
    }

    private void create(BufferedReader reader) {
        try {
            System.out.println("UserController.create");
            System.out.print("Имя: ");
            String name = reader.readLine();
            System.out.print("Возраст: ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            User user = new User();
            user.setAge(age);
            user.setName(name);
            userService.create(user);
        } catch (Exception e) {
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void update(BufferedReader reader) {
        try {
            System.out.println("UserController.update");
            System.out.print("Введи id: ");
            String id = reader.readLine();
            System.out.print("Имя: ");
            String name = reader.readLine();
            System.out.print("Возраст: ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            User user = new User();
            user.setId(id);
            user.setAge(age);
            user.setName(name);
            userService.update(user);
        } catch (IOException e) {
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void delete(BufferedReader reader) {
        System.out.println("UserController.delete");
        try {
            System.out.print("Введите id: ");
            String id = reader.readLine();
            userService.delete(id);
        } catch (IOException e) {
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void findById(BufferedReader reader) {
        System.out.println("UserController.findById");
        try {
            System.out.print("Введите id: ");
            String id = reader.readLine();
            User user = userService.findById(id);
            System.out.println("Юзер = " + user);
        } catch (IOException e) {
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void findAll(BufferedReader reader) {
        System.out.println("UserController.findAll");
        User[] users = userService.findAll();
        if (users != null && users.length != 0) {
            for (User user : users) {
                System.out.println("Юзер = " + user);
            }
        } else {
            System.out.println("Список пуст");
        }
    }
}
