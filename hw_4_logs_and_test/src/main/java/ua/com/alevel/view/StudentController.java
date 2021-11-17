package ua.com.alevel.view;

import ua.com.alevel.entity.Student;
import ua.com.alevel.service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;

public class StudentController {

    private final StudentService studentService = new StudentService();

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
            System.out.println("Проблемка : = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println("Создать студента -> 1");
        System.out.println("Изменить данные студента -> 2");
        System.out.println("Удалить студента -> 3");
        System.out.println("Найти студента по Id -> 4");
        System.out.println("Список всех студентов -> 5");
        System.out.println("Назад -> 0");
        System.out.println();
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
            System.out.print("Имя: ");
            String name = reader.readLine();
            System.out.print("Возраст: ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            Student student = new Student();
            student.setAge(age);
            student.setName(name);
            studentService.create(student);
        } catch (Exception e) {
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void update(BufferedReader reader) {
        try {
            System.out.println("StudentController.update");
            System.out.print("Введи id: ");
            String id = reader.readLine();
            System.out.print("Имя: ");
            String name = reader.readLine();
            System.out.print("Возраст: ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            Student student = new Student();
            student.setId(id);
            student.setAge(age);
            student.setName(name);
            studentService.update(student);
        } catch (IOException e) {
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
            System.out.println("Список пуст");
        }
    }

}
