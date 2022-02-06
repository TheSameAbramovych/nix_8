package ua.com.alevel.hw_9_hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.alevel.hw_9_hibernate.controller.dto.CreateGroupRequest;
import ua.com.alevel.hw_9_hibernate.controller.dto.PageAndSizeData;
import ua.com.alevel.hw_9_hibernate.controller.dto.SortData;
import ua.com.alevel.hw_9_hibernate.controller.dto.StudentGroupIds;
import ua.com.alevel.hw_9_hibernate.entity.Group;
import ua.com.alevel.hw_9_hibernate.entity.Student;
import ua.com.alevel.hw_9_hibernate.service.GroupService;
import ua.com.alevel.hw_9_hibernate.service.StudentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController extends BaseController {

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
    public String createGroupForm(CreateGroupRequest request) {
        return "group/create";
    }

    @PostMapping("/create")
    public String createGroup(@Valid CreateGroupRequest request, BindingResult br, Model model) {
        if (br.hasErrors()) {
            showError(br, model);
            return "group/create";
        }
        boolean error = errorHandling(() -> {
            Group group = new Group();
            group.setName(request.getName());
            group.setHeadman(studentService.findById(request.getHeadmanId()));
            groupService.save(group);
        }, model);

        if (error) {
            return "group/create";
        }

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
        Student student = studentService.findById(studentId);
        groupService.deleteStudent(id, student);
        return "redirect:" + referer;
    }

    @PostMapping("/students/add")
    public String addStudentToGroup(@Valid StudentGroupIds ids, BindingResult br, RedirectAttributes redirectAttributes) {
        if (br.hasErrors()) {
            showError(br, redirectAttributes);
            return "redirect:/groups/details/" + ids.getGroupId();
        }
        Student student = studentService.findById(ids.getStudentId());

        if (errorHandling(() -> groupService.addStudent(ids.getGroupId(), student), redirectAttributes)) {
            return "redirect:/groups/details/" + ids.getGroupId();
        }

        return "redirect:/groups/details/" + ids.getGroupId();
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
    public String updateGroup(@Valid Group group, BindingResult br, Model model) {
        if (br.hasErrors()) {
            showError(br, model);
            return "group/update";
        }
        if (errorHandling(() -> groupService.update(group), model)) {
            return "group/update";
        }
        return "redirect:/groups";
    }
}
