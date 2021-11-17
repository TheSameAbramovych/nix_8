package ua.com.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.GroupDao;
import ua.com.alevel.dao.StudentDao;
import ua.com.alevel.entity.Group;

public class GroupService {

    private final GroupDao groupDao;
    private final StudentDao studentDao;
    private final Logger loggerError = LoggerFactory.getLogger("error");
    private final Logger loggerWarn = LoggerFactory.getLogger("warn");
    private final Logger loggerInfo = LoggerFactory.getLogger("info");

    public GroupService() {
        groupDao = new GroupDao();
        studentDao = new StudentDao();
    }

    public GroupService(GroupDao groupDao, StudentDao studentDao) {
        this.groupDao = groupDao;
        this.studentDao = studentDao;
    }

    public void create(Group group) {
        loggerInfo.info("Create group: " + group);
        if (group.getStudentIds() != null) {
            for (int i = 0; i < group.getStudentIds().getLength(); i++) {
                String studentId = group.getStudentIds().get(i);
                if (studentDao.findById(studentId) == null) {
                    System.out.println("Студент: '" + studentId + "' не найден");
                    loggerError.error("Student not found: " + studentId);
                    return;
                }
            }
            if (group.getStudentIds().indexOf(group.getHeadman()) == -1 && !group.getHeadman().isEmpty()) {
                System.out.println("Староста: '" + group.getHeadman() + "' не есть студентом этой групы");
                loggerError.error("Headman not found in this group: " + group.getHeadman());
                return;
            }
            groupDao.create(group);
        }
    }

    public void update(Group group) {
        loggerWarn.warn("Update group" + group);
        Group groupForUpdate = findById(group.getId());
        if (groupForUpdate == null) {
            loggerError.error("Group not found: " + group.getId());
            System.out.println("Група: '" + group.getId() + "' не найдена");
            return;
        }

        group.setName(group.getName().isEmpty() ? groupForUpdate.getName() : group.getName());
        loggerInfo.info("Change name group on: " + group.getName());
        group.setHeadman(group.getHeadman().isEmpty() ? groupForUpdate.getHeadman() : group.getHeadman());
        loggerInfo.info("Change headman on: " + group.getHeadman());

        if (group.getStudentIds() != null) {
            for (int i = 0; i < group.getStudentIds().getLength(); i++) {
                String studentId = group.getStudentIds().get(i);
                if (studentDao.findById(studentId) == null) {
                    System.out.println("Студент: '" + studentId + "' не найден");
                    loggerError.error("Student not found: " + studentId);
                    return;
                }
                loggerInfo.info("Add student: " + studentId);
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
        loggerInfo.info("Remove group: " + id);

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
            loggerError.error("Group not found: " + groupId);
            return;
        }
        group.removeStudentId(studentId);
        loggerInfo.info("Remove student: " + studentId);
        update(group);
    }
}
