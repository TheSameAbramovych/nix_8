package ua.com.alevel.alevel.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.alevel.entity.Group;
import ua.com.alevel.alevel.service.GroupService;
import ua.com.alevel.alevel.utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;


public class GroupController {

    private final GroupService groupService = new GroupService();
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
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("""
                Создать групу -> 1
                Изменить данные групы -> 2
                Удалить групу -> 3
                Найти групу по Id -> 4
                Список всех груп -> 5
                Удалить студента из групы -> 6
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
            case "6" -> removeStudentId(reader);
        }
    }

    private void create(BufferedReader reader) {
        try {
            System.out.println("GroupController.create");
            Group group = new Group();
            System.out.print("Имя групы: ");
            String name = reader.readLine();
            group.setName(name);

            System.out.print("Студенты: ");
            for (String studentId : IOUtils.readStringArray()) {
                if (!studentId.isEmpty()) {
                    group.addStudentId(studentId);
                }
            }

            System.out.print("Староста: ");
            String headManId = reader.readLine();
            if (!headManId.isEmpty()) {
                group.setHeadman(headManId);
            } else {
                group.setHeadman("");
            }

            groupService.create(group);
        } catch (Exception e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void update(BufferedReader reader) {
        try {
            System.out.println("GroupController.update");
            Group group = new Group();

            System.out.print("Введи id: ");
            String id = reader.readLine();
            group.setId(id);

            System.out.print("Имя: ");
            String name = reader.readLine();
            group.setName(name);

            System.out.print("Id Старосты: ");
            String headManId = reader.readLine();
            group.setHeadman(headManId);

            System.out.print("Id Студентов: ");
            for (String studentId : IOUtils.readStringArray()) {
                if (!studentId.isEmpty()) {
                    group.addStudentId(studentId);
                }
            }


            groupService.update(group);
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void delete(BufferedReader reader) {
        System.out.println("GroupController.delete");
        try {
            System.out.print("Введите id: ");
            String id = reader.readLine();
            groupService.delete(id);
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void findById(BufferedReader reader) {
        System.out.println("GroupController.findById");
        try {
            System.out.print("Введите id: ");
            String id = reader.readLine();
            Group group = groupService.findById(id);
            System.out.println("Група = " + group);
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    private void findAll() {
        System.out.println("GroupController.findAll");
        Group[] groups = groupService.findAll();
        if (groups != null && groups.length != 0) {
            for (Group group : groups) {
                System.out.println("Група = " + group);
            }
        } else {
            LOGGER_ERROR.error("list of group is empty");
            System.out.println("Список пуст");
        }
    }

    private void removeStudentId(BufferedReader reader) {
        System.out.println("GroupController.removeStudentId");
        try {
            System.out.print("Введите id групы: ");
            String idGroup = reader.readLine();

            System.out.print("Введите id студента: ");
            String id = reader.readLine();

            groupService.removeStudentId(idGroup, id);
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

}
