package ua.com.alevel.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.alevel.dao.GroupDao;
import ua.com.alevel.dao.StudentDao;
import ua.com.alevel.entity.Group;
import ua.com.alevel.entity.Student;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

    public static final Random RANDOM = new Random();
    @Mock
    private GroupDao groupDao;

    @Mock
    private StudentDao studentDao;

    @InjectMocks
    private GroupService groupService;


    @Test
    public void findAll_ok() {
        Group[] groups = {createGroup(), createGroup()};

        when(groupDao.findAll()).thenReturn(groups);

        Group[] groupsAll = groupService.findAll();

        assertArrayEquals(groups, groupsAll);
        verify(groupDao).findAll();
    }

    @Test
    public void update_ok() {
        Group groupOld = createGroup();
        Group groupNew = createGroup();
        groupNew.setName(StringUtils.EMPTY);
        groupNew.addStudentId(groupNew.getHeadman());

        when(groupDao.findById(groupNew.getId())).thenReturn(groupOld);
        when(studentDao.findById(groupNew.getStudentIds().get(0))).thenReturn(createStudent());
        when(studentDao.findById(groupNew.getStudentIds().get(1))).thenReturn(createStudent());

        groupService.update(groupNew);

        assertEquals(groupOld.getName(), groupNew.getName());
        verify(studentDao).findById(groupNew.getStudentIds().get(0));
        verify(studentDao).findById(groupNew.getStudentIds().get(1));
        verify(groupDao).update(groupNew);
    }

    @Test
    public void update_groupNotExists_fail() {
        Group group = createGroup();

        when(groupDao.findById(group.getId())).thenReturn(null);

        groupService.update(group);

        verify(groupDao).findById(group.getId());
        verify(groupDao, never()).update(group);
    }

    @Test
    public void delete_ok() {
        Group group = createGroup();

        groupService.delete(group.getId());
        verify(groupDao).delete(group.getId());
    }

    @Test
    public void findById_ok() {
        Group group = createGroup();

        groupService.findById(group.getId());
        verify(groupDao).findById(group.getId());
    }

    @Test
    public void create_ok() {
        Group group = createGroup();
        group.addStudentId(group.getHeadman());

        when(studentDao.findById(group.getHeadman())).thenReturn(createStudent());
        when(studentDao.findById(group.getStudentIds().get(0))).thenReturn(createStudent());

        groupService.create(group);

        verify(groupDao).create(group);
        verify(studentDao).findById(group.getHeadman());
        verify(studentDao).findById(group.getStudentIds().get(0));
    }

    @Test
    public void create_studentNotExists_ok() {
        Group group = createGroup();
        group.addStudentId(group.getHeadman());

        groupService.create(group);

        verify(groupDao, never()).create(group);
        verify(studentDao, never()).findById(group.getHeadman());
        verify(studentDao).findById(group.getStudentIds().get(0));
    }

    @Test
    public void create_headmenNotExistsInStudent_ok() {
        Group group = createGroup();

        when(studentDao.findById(group.getStudentIds().get(0))).thenReturn(createStudent());

        groupService.create(group);

        verify(groupDao, never()).create(group);
        verify(studentDao, never()).findById(group.getHeadman());
        verify(studentDao).findById(group.getStudentIds().get(0));
    }

    @Test
    public void create_nameIsEmpty_ok() {
        Group group = createGroup();
        group.addStudentId(group.getHeadman());
        group.setName(StringUtils.EMPTY);

        when(studentDao.findById(group.getHeadman())).thenReturn(createStudent());
        when(studentDao.findById(group.getStudentIds().get(0))).thenReturn(createStudent());

        groupService.create(group);

        verify(groupDao, never()).create(group);
        verify(studentDao).findById(group.getHeadman());
        verify(studentDao).findById(group.getStudentIds().get(0));
    }

    private Group createGroup() {
        Group group = new Group();
        group.setId(UUID.randomUUID().toString());
        group.setName(UUID.randomUUID().toString());
        group.setHeadman(UUID.randomUUID().toString());
        group.addStudentId(UUID.randomUUID().toString());

        return group;
    }

    private Student createStudent() {
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName(UUID.randomUUID().toString());
        student.setAge(RANDOM.nextInt());
        student.setEmail(UUID.randomUUID().toString());
        return student;
    }
}