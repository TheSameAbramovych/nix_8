package ua.com.hw4.service;

import ua.com.hw4.dao.GroupDao;
import ua.com.hw4.dao.StudentDao;
import ua.com.hw4.entity.Group;

public class GroupService {

    private final GroupDao groupDao = new GroupDao();
    private final StudentDao studentDao = new StudentDao();

    public void create(Group group) {
        for (int i = 0; i < group.getStudentIds().getLength(); i++) {
            String studentId = group.getStudentIds().get(i);
            if (studentDao.findById(studentId) == null) {
                System.out.println("Student: '" + studentId + "' not exists");
                return;
            }
        }
        if (group.getStudentIds().indexOf(group.getHeadman()) == -1 && !group.getHeadman().isEmpty()) {
            System.out.println("Headman: '" + group.getHeadman() + "' is not include to group`s students");
            return;
        }
        groupDao.create(group);
    }

    public void update(Group group) {
        groupDao.update(group);
    }

    public void delete(String id) {
        groupDao.delete(id);
    }

    public Group findById(String id) {
        return groupDao.findById(id);
    }

    public Group[] findAll() {
        return groupDao.findAll();
    }
}
