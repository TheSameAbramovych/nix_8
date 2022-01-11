package ua.com.alevel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.controller.dto.PageAndSizeData;
import ua.com.alevel.controller.dto.SortData;
import ua.com.alevel.controller.dto.StudentGroupIds;
import ua.com.alevel.persistence.entity.Group;
import ua.com.alevel.service.GroupService;
import ua.com.alevel.service.StudentService;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private final StudentService studentService;

    @Autowired
    public GroupController(GroupService groupService, StudentService studentService) {
        this.groupService = groupService;
        this.studentService = studentService;
    }

    @GetMapping()
    public String findAll(Model model, PageAndSizeData pageAndSizeData, SortData sortData) {
        pageAndSizeData = RequestHelper.getValidPageAndSizeData(pageAndSizeData);
        sortData = RequestHelper.getValidSortData(sortData);

        List<Group> groups = groupService.findAll(pageAndSizeData, sortData);
        int size = pageAndSizeData.getSize();
        long pageCount = groupService.count() % size == 0 ? groupService.count() / size : groupService.count() / size + 1;

        model.addAttribute("pageCount", pageCount);
        model.addAttribute("groups", groups);
        model.addAttribute("pageAndSize", pageAndSizeData);
        model.addAttribute("sortData", sortData);
        return "group/list";
    }

    @GetMapping("/create")
    public String createGroupForm(Group group) {
        return "group/create";
    }

    @PostMapping("/create")
    public String createGroup(Group group) {
        groupService.save(group);
        return "redirect:/groups";
    }

    @GetMapping("/details/{id}")
    public String detailsGroup(@PathVariable("id") Long id, Model model) {
        model.addAttribute("group", groupService.findById(id));
        model.addAttribute("students", studentService.findByGroup(id));
        model.addAttribute("ids", new StudentGroupIds());
        return "group/details";
    }

    @GetMapping("/{id}/students/delete/{student_id}")
    public String deleteStudentFromGroup(@PathVariable("id") Long id, @PathVariable("student_id") Long studentId, @RequestHeader("Referer") String referer) {
        groupService.deleteStudent(id, studentId);
        return "redirect:" + referer;
    }

    @PostMapping("/students/add")
    public String addStudentToGroup(StudentGroupIds ids, @RequestHeader("Referer") String referer) {
        groupService.addStudent(ids.getGroupId(), ids.getStudentId());
        return "redirect:" + referer;
    }

    @GetMapping("/delete/{id}")
    public String deleteGroup(@PathVariable("id") Long id) {
        groupService.delete(id);
        return "redirect:/groups";
    }

    @GetMapping("/update/{id}")
    public String updateGroupForm(@PathVariable("id") Long id, Model model) {
        Group group = groupService.findById(id);
        model.addAttribute("group", group);
        return "group/update";
    }

    @PostMapping("/update")
    public String updateGroup(Group group) {
        groupService.update(group);
        return "redirect:/groups";
    }
}
