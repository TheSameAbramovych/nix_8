package ua.com.alevel.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.alevel.dao.GroupDao;
import ua.com.alevel.alevel.dao.StudentDao;
import ua.com.alevel.alevel.entity.Group;
import ua.com.alevel.alevel.utils.CustomList;

public class GroupService {
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    private final GroupDao groupDao;
    private final StudentDao studentDao;


    public GroupService() {
        groupDao = new GroupDao();
        studentDao = new StudentDao();
    }

    public GroupService(GroupDao groupDao, StudentDao studentDao) {
        this.groupDao = groupDao;
        this.studentDao = studentDao;
    }

    public void create(Group group) {
        LOGGER_INFO.info("Create group: " + group);
        if (group.getStudentIds() != null) {
            for (int i = 0; i < group.getStudentIds().getLength(); i++) {
                String studentId = group.getStudentIds().get(i);
                if (studentDao.findById(studentId) == null) {
                    System.out.println("Студент: '" + studentId + "' не найден");
                    LOGGER_ERROR.error("Student not found: " + studentId);
                    return;
                }
            }

            if (group.getStudentIds().indexOf(group.getHeadman()) == -1 && !group.getHeadman().isEmpty()) {
                System.out.println("Староста: '" + group.getHeadman() + "' не есть студентом этой групы");
                LOGGER_ERROR.error("Headman not found in this group: " + group.getHeadman());
                return;
            }

            if (!group.getName().isEmpty()) {
                groupDao.create(group);
            } else {
                System.out.println("Невозможно создать групу без имени!");
                LOGGER_ERROR.error("Group not create: " + group);
            }
        }
    }

    public void update(Group group) {
        LOGGER_WARN.warn("Update group" + group);
        Group groupForUpdate = findById(group.getId());
        if (groupForUpdate == null) {
            LOGGER_ERROR.error("Group not found: " + group.getId());
            System.out.println("Група: '" + group.getId() + "' не найдена");
            return;
        }

        if (group.getStudentIds().getLength() == 0) {
            for (int i = 0; i < groupForUpdate.getStudentIds().getLength(); i++) {
                CustomList<String> students = groupForUpdate.getStudentIds();
                group.addStudentId(students.get(i));
            }
        }

        group.setName(group.getName().isEmpty() ? groupForUpdate.getName() : group.getName());
        LOGGER_INFO.info("Change name group on: " + group.getName());
        group.setHeadman(group.getHeadman().isEmpty() ? groupForUpdate.getHeadman() : group.getHeadman());
        LOGGER_INFO.info("Change headman on: " + group.getHeadman());

        if (group.getStudentIds() != null) {
            for (int i = 0; i < group.getStudentIds().getLength(); i++) {
                String studentId = group.getStudentIds().get(i);
                if (studentDao.findById(studentId) == null) {
                    System.out.println("Студент: '" + studentId + "' не найден");
                    LOGGER_ERROR.error("Student not found: " + studentId);
                    return;
                }
                LOGGER_INFO.info("Add student: " + studentId);
            }
        } else {
            for (int i = 0; i < groupForUpdate.getStudentIds().getLength(); i++) {
                String studentId = group.getStudentIds().get(i);
                group.addStudentId(studentId);
            }
        }

        groupDao.update(group);
    }

    public void delete(String id) {
        groupDao.delete(id);
        LOGGER_INFO.info("Remove group: " + id);
    }

    public Group findById(String id) {
        return groupDao.findById(id);
    }

    public Group[] findAll() {
        return groupDao.findAll();
    }

    public void removeStudentId(String groupId, String studentId) {
        Group group = findById(groupId);
        if (group == null) {
            System.out.println("Група: '" + groupId + "' не найдена");
            LOGGER_ERROR.error("Group not found: " + groupId);
            return;
        }
        group.removeStudentId(studentId);
        LOGGER_INFO.info("Remove student: " + studentId);
        update(group);
    }
}
