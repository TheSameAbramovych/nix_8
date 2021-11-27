package ua.com.alevel.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;

public class StudentController {

    private final StudentService studentService = new StudentService();
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    public void run(BufferedReader reader) {
        System.out.println();
        System.out.println("Выбирай : ");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                if (position.equals("0")) {
                    break;
                }
                crud(position, reader);
                runNavigation();
            }
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
            System.out.println("Проблемка : = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("""
                Создать студента -> 1
                Изменить данные студента -> 2
                Удалить студента -> 3
                Найти студента по Id -> 4
                Список всех студентов -> 5
                Назад -> 0
                """);
    }

    private void crud(String position, BufferedReader reader) {
        switch (position) {
            case "1" -> create(reader);
            case "2" -> update(reader);
            case "3" -> delete(reader);
            case "4" -> findById(reader);
            case "5" -> findAll();
        }
    }

    private void create(BufferedReader reader) {
        try {
            System.out.println("StudentController.create");
            Student student = new Student();

            System.out.print("Имя: ");
            String name = reader.readLine();
            student.setName(name);

            System.out.print("Возраст: ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            student.setAge(age);

            System.out.print("Эл. адрес: ");
            String email = reader.readLine();
            if (email.contains("@")) {
                student.setEmail(email);
            } else {
                LOGGER_ERROR.error("incorrect email");
                System.out.println("Некоректный Эл. адрес!");
            }

            studentService.create(student);
        } catch (Exception e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void update(BufferedReader reader) {
        try {
            System.out.println("StudentController.update");
            Student student = new Student();

            System.out.print("Введи id: ");
            String id = reader.readLine();
            student.setId(id);

            System.out.print("Имя: ");
            String name = reader.readLine();
            student.setName(name);

            System.out.print("Возраст: ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            student.setAge(age);

            System.out.print("Эл. адрес: ");
            String email = reader.readLine();
            if (!email.isEmpty()) {
                char[] emailChar = email.toCharArray();
                char validateOfEmail = '@';
                for (char c : emailChar) {
                    if (c == validateOfEmail) {
                        student.setEmail(email);
                    }
                }
            } else {
                LOGGER_ERROR.error("incorrect email");
                System.out.println("Некоректный Эл. адрес!");
            }

            studentService.update(student);
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void delete(BufferedReader reader) {
        System.out.println("StudentController.delete");
        try {
            System.out.print("Введите id: ");
            String id = reader.readLine();
            studentService.delete(id);
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void findById(BufferedReader reader) {
        System.out.println("StudentController.findById");
        try {
            System.out.print("Введите id: ");
            String id = reader.readLine();
            Student student = studentService.findById(id);
            System.out.println("Студент = " + student);
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void findAll() {
        System.out.println("StudentController.findAll");
        Student[] students = studentService.findAll();
        if (students != null && students.length != 0) {
            for (Student student : students) {
                System.out.println("Студент = " + student);
            }
        } else {
            LOGGER_ERROR.error("list of student is empty");
            System.out.println("Список пуст");
        }
    }

}
